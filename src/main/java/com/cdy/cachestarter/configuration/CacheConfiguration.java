package com.cdy.cachestarter.configuration;

import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存配置类
 * Created by 陈东一
 * 2018/8/25 18:24
 */
@Configuration
public class CacheConfiguration {
    
    @Bean
    public CacheSupport cacheSupport(){
        return new CacheSupport();
    }
    
    @Bean
    public CachePutInterceptor cachePutInterceptor(){
        return new CachePutInterceptor();
    }
  
    @Bean
    public CachePutsInterceptor cachePutsInterceptor(){
        return new CachePutsInterceptor();
    }
    
    @Bean
    public CacheDelInterceptor cacheDelInterceptor(){
        return new CacheDelInterceptor();
    }
    
    @Bean
    public CacheDelsInterceptor cacheDelsInterceptor(){
        return new CacheDelsInterceptor();
    }
    
    @Bean
    public DefaultPointcutAdvisor cachePutAdvisor(){
        DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor(cachePutInterceptor());
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null ,CachePut.class);
        defaultPointcutAdvisor.setPointcut(pointcut);
        return defaultPointcutAdvisor;
    }
    @Bean
    public DefaultPointcutAdvisor cachePutsAdvisor(){
        DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor(cachePutsInterceptor());
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null ,CachePuts.class);
        defaultPointcutAdvisor.setPointcut(pointcut);
        return defaultPointcutAdvisor;
    }
    
    @Bean
    public DefaultPointcutAdvisor cacheDelAdvisor(){
        DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor(cacheDelInterceptor());
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null ,CacheDel.class);
        defaultPointcutAdvisor.setPointcut(pointcut);
        return defaultPointcutAdvisor;
    }
    
    @Bean
    public DefaultPointcutAdvisor cacheDelsAdvisor(){
        DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor(cacheDelsInterceptor());
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null ,CacheDels.class);
        defaultPointcutAdvisor.setPointcut(pointcut);
        return defaultPointcutAdvisor;
    }
    
}
