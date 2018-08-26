package com.cdy.cachestarter.configuration;

import com.cdy.common.util.cache.CacheUtil;
import com.cdy.common.util.cache.redis.RedisUtil;
import com.cdy.redisstarter.config.SingleRedisConfiguration;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存配置类
 * Created by 陈东一
 * 2018/8/25 18:24
 */
@Configuration
@AutoConfigureAfter(SingleRedisConfiguration.class)
public class CacheConfiguration {

    
    @Bean
    public CachePutInterceptor cachePutInterceptor(){
        return new CachePutInterceptor();
    }
    
    @Bean
    public CacheDelInterceptor cacheDelInterceptor(){
        return new CacheDelInterceptor();
    }
    
    @Bean
    public DefaultPointcutAdvisor cachePutAdvisor(){
        DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor(cachePutInterceptor());
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null ,CachePut.class);
        defaultPointcutAdvisor.setPointcut(pointcut);
        return defaultPointcutAdvisor;
    }
    
    @Bean
    public DefaultPointcutAdvisor advisor(){
        DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor(cacheDelInterceptor());
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null ,CacheDel.class);
        defaultPointcutAdvisor.setPointcut(pointcut);
        return defaultPointcutAdvisor;
    }
    
    @Bean
    public CacheUtil cacheUtil(RedisUtil redisUtil){
        return redisUtil;
    }
    

}
