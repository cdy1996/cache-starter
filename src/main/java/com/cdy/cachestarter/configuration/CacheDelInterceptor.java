package com.cdy.cachestarter.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * 缓存删除的后置切面
 * Created by 陈东一
 * 2018/8/25 17:43
 */
public class CacheDelInterceptor extends CacheSupport implements AfterReturningAdvice{
    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        cacheDelInvoke(method, method.getAnnotation(CacheDel.class), args);
    }
    
   
    
    
}
