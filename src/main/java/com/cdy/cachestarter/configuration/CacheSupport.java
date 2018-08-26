package com.cdy.cachestarter.configuration;

import com.cdy.common.util.cache.CacheUtil;
import com.cdy.common.util.serialization.JsonUtil;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 缓存的辅助类
 * Created by 陈东一
 * 2018/8/25 21:08
 */
public class CacheSupport {
    
    private static final Logger log = LoggerFactory.getLogger(CacheSupport.class);
    private static final Object LOCK = new Object();
    
    @Autowired
    CacheUtil cacheUtil;
    @Autowired
    CacheProperties cacheProperties;
    
    
    /**
     * 解析缓存的key
     *
     * @param key    注解上的key
     * @param method 对应的方法
     * @param args   对应方法的参数
     * @param prefix 缓存的key前缀
     * @return
     */
    public String parseKey(String key, Method method, Object[] args, String prefix) {
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        ExpressionParser parser = new SpelExpressionParser();
        
        ParameterNameDiscoverer parameterNameDiscoverer =
                new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        
        for (int i = 0; i < parameterNames.length; i++) {
            evaluationContext.setVariable(parameterNames[i], args[i]);
        }
        String cacheKey = parser.parseExpression(prefix + key, new TemplateParserContext())
                .getValue(evaluationContext, String.class);
        log.info("cache key is {}", cacheKey);
        return cacheKey;
        
    }
    
    public void cacheDelInvoke(Method method, CacheDel annotation, Object[] args) {
        String key = parseKey(annotation.key(), method, args, cacheProperties.getPrefix());
        try {
            cacheUtil.delete(key);
            log.error("{} cache del success", key);
        } catch (Throwable throwable) {
            log.error("cache del error", throwable);
        }
    }
    
    public Object cachePutInvoke(MethodInvocation methodInvocation) throws Throwable {
        Object searchResult = null;
        String cacheResult = null;
        CachePut annotation = methodInvocation.getMethod().getAnnotation(CachePut.class);
        Class<?> returnType = methodInvocation.getMethod().getReturnType();
        
        try {
            String key = parseKey(annotation.key(), methodInvocation.getMethod(), methodInvocation.getArguments(), cacheProperties.getPrefix());
            int expire = annotation.expire() == 0 ? cacheProperties.getExpireTime() : annotation.expire();
            cacheResult = cacheUtil.get(key);
            if (cacheResult == null) {
                //防止多线程查询数据库
                synchronized (LOCK) {
                    //有线程查到后释放锁,其他线程则可以直接从缓存中拿到查询结果
                    if ((cacheResult = cacheUtil.get(key)) != null) {
                        log.info("cache not hit but is synchronized \n {}", cacheResult);
                        searchResult = convert(cacheResult, annotation, returnType);
                    } else {
                        searchResult = methodInvocation.proceed();
                        cacheResult = JsonUtil.toString(searchResult);
                        cacheUtil.set(key, JsonUtil.toString(searchResult), expire);
                        log.info("cache not hit \n {}", cacheResult);
                    }
                }
            } else {
                log.info("cache hit \n {}", cacheResult);
                searchResult = convert(cacheResult, annotation, returnType);
            }
        } catch (Throwable throwable) {
            log.error("cache is error \n", throwable);
            //可能是缓存问题,可能是业务问题,这里统一让业务在执行一次,错了直接上抛异常
            searchResult = methodInvocation.proceed();
            //这里set也可能存在异常,所以不缓存了
//            cacheUtil.set(key, JsonUtil.toString(searchResult), expire);
        }
        return searchResult;
    }
    
    public Object cachePutsInvoke(MethodInvocation methodInvocation) throws Throwable {
        Object searchResult = null;
        String cacheResult = null;
        CachePuts annotation = methodInvocation.getMethod().getAnnotation(CachePuts.class);
        CachePut[] cachePuts = annotation.value();
        Class<?> returnType = methodInvocation.getMethod().getReturnType();
      
        try {
            String key = "";
            int expire = 0;
            //循环读取缓存值
            for (CachePut cachePut : cachePuts) {
                key = parseKey(cachePut.key(), methodInvocation.getMethod(), methodInvocation.getArguments(), cacheProperties.getPrefix());
                cacheResult = cacheUtil.get(key);
                if (cacheResult != null) {
                    searchResult = convert(cacheResult, cachePut, returnType);
                    break;
                }
            }
            //如果都没有则去查库
            if (cacheResult == null) {
                //防止多线程查询数据库
                synchronized (LOCK) {
                    //有线程查到后释放锁,其他线程则可以直接从缓存中拿到查询结果
                    key = parseKey(cachePuts[0].key(), methodInvocation.getMethod(), methodInvocation.getArguments(), cacheProperties.getPrefix());
                    cacheResult = cacheUtil.get(key);
                    if (cacheResult != null) {
                        log.info("cache not hit but is synchronized \n {}", cacheResult);
                        searchResult = convert(cacheResult, cachePuts[0], returnType);
                    } else {
                        searchResult = methodInvocation.proceed();
                        cacheResult = JsonUtil.toString(searchResult);
                        for (CachePut cachePut : cachePuts) {
                            key = parseKey(cachePut.key(), methodInvocation.getMethod(), methodInvocation.getArguments(), cacheProperties.getPrefix());
                            expire = cachePut.expire() == 0 ? cacheProperties.getExpireTime() : cachePut.expire();
                            cacheUtil.set(key, JsonUtil.toString(searchResult), expire);
                        }
                        log.info("cache not hit \n {}", cacheResult);
                    }
                }
            }
            //如果有一个能得到缓存对象,则刷新其他的缓存注解
            else {
                log.info("cache hit \n {}", cacheResult);
                for (CachePut cachePut : cachePuts) {
                    key = parseKey(cachePut.key(), methodInvocation.getMethod(), methodInvocation.getArguments(), cacheProperties.getPrefix());
                    expire = cachePut.expire() == 0 ? cacheProperties.getExpireTime() : cachePut.expire();
                    if (!cacheUtil.exist(key)) {
                        cacheUtil.set(key, JsonUtil.toString(searchResult), expire);
                    }
                }
            }
        } catch (Throwable throwable) {
            log.error("cache is error \n", throwable);
            //可能是缓存问题,可能是业务问题,这里统一让业务在执行一次,错了直接上抛异常
            searchResult = methodInvocation.proceed();
        }
        return searchResult;
    }
    
    private Object convert(String cacheResult, CachePut annotation, Class<?> returnType) {
        Object searchResult = null;
        if (returnType == List.class) {
            searchResult = JsonUtil.parseArray(cacheResult, annotation.type());
        } else {
            searchResult = JsonUtil.parseObject(cacheResult, returnType);
        }
        return searchResult;
    }
    
    
}
