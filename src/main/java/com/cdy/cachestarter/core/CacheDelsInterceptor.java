package com.cdy.cachestarter.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

/**
 * 缓存删除的后置切面
 * Created by 陈东一
 * 2018/8/25 17:43
 */
@Slf4j
public class CacheDelsInterceptor implements AfterReturningAdvice{

    @Autowired
    private CacheSupport cacheSupport;
    
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        if (cacheSupport.getCacheProperties().getEnable().equalsIgnoreCase("true")) {
            CacheDels annotation = method.getAnnotation(CacheDels.class);
            for (CacheDel cacheDel : annotation.value()) {
                cacheSupport.cacheDelInvoke(method, cacheDel, args);
            }
        }
    }
    

}
