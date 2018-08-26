package com.cdy.cachestarter.configuration;

import com.cdy.common.util.cache.CacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

import static com.cdy.cachestarter.configuration.CacheSupport.parseKey;

/**
 * 缓存删除的后置切面
 * Created by 陈东一
 * 2018/8/25 17:43
 */
public class CacheDelInterceptor implements AfterReturningAdvice{
    
    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private CacheProperties cacheProperties;
    
    
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        log.info("缓存的后置切面");
        CacheDel[] annotationsByType = method.getAnnotationsByType(CacheDel.class);
        for (CacheDel annotation : annotationsByType) {
            String key = parseKey(annotation.key(), method, args, cacheProperties.getPrefix());
            try {
                cacheUtil.delete(key);
            } catch (Throwable throwable) {
                log.error("cache del error");
            }
        }
    }
    

}
