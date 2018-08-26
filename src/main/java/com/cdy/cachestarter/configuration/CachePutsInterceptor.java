package com.cdy.cachestarter.configuration;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存添加的环绕切面
 * Created by 陈东一
 * 2018/8/25 17:43
 */
public class CachePutsInterceptor extends CacheSupport implements MethodInterceptor {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        return cachePutsInvoke(methodInvocation);
    }
    
    
}
