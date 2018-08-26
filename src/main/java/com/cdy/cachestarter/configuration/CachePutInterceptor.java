package com.cdy.cachestarter.configuration;

import com.cdy.common.util.cache.CacheUtil;
import com.cdy.common.util.serialization.JsonUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.cdy.cachestarter.configuration.CacheSupport.parseKey;

/**
 * 缓存添加的环绕切面
 * Created by 陈东一
 * 2018/8/25 17:43
 */
public class CachePutInterceptor implements MethodInterceptor {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private CacheProperties cacheProperties;
    private static final Object LOCK = new Object();
    
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
       
        Object searchResult = null;
        String cacheResult = null;
        CachePut annotation = methodInvocation.getMethod().getAnnotation(CachePut.class);
        String key = "";
        int expire = annotation.expire() == 0 ? cacheProperties.getExpireTime() : annotation.expire();
        try {
            key = parseKey(annotation.key(), methodInvocation.getMethod(), methodInvocation.getArguments(), cacheProperties.getPrefix());
            cacheResult = cacheUtil.get(key);
            if (cacheResult == null) {
                //防止多线程查询数据库
                synchronized (LOCK) {
                    //有线程查到后释放锁,其他线程则可以直接从缓存中拿到查询结果
                    if ((cacheResult = cacheUtil.get(key)) != null) {
                        log.info("cache not hit but is synchronized \n {}", cacheResult);
                        searchResult = convent(cacheResult, annotation);
                    } else {
                        searchResult = methodInvocation.proceed();
                        cacheResult = JsonUtil.toString(searchResult);
                        cacheUtil.set(key, JsonUtil.toString(searchResult), expire);
                        log.info("cache not hit \n {}", cacheResult);
                    }
                }
            } else {
                log.info("cache hit \n {}", cacheResult);
                searchResult = convent(cacheResult, annotation);
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
    
    private Object convent(String cacheResult, CachePut annotation) {
        boolean list = annotation.isList();
        Object searchResult = null;
        if (list) {
            searchResult = JsonUtil.parseArray(cacheResult, annotation.type());
        } else {
            searchResult = JsonUtil.parseObject(cacheResult, annotation.type());
        }
        return searchResult;
    }
    
}
