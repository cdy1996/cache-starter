package com.cdy.cachestarter.core;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 缓存添加的环绕切面
 * Created by 陈东一
 * 2018/8/25 17:43
 */
@Slf4j
public class CachePutInterceptor implements MethodInterceptor {
    
    @Autowired
    private CacheSupport cacheSupport;
    
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (cacheSupport.getCacheProperties().getEnable().equalsIgnoreCase("true")) {
            return cacheSupport.cachePutInvoke(methodInvocation);
        } else {
            return methodInvocation.proceed();
        }
    }
    
    
}
