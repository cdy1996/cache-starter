package com.cdy.cachestarter.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启缓存注解
 * Created by 陈东一
 * 2018/8/25 18:32
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableConfigurationProperties({CacheProperties.class})
@Import({CacheSelector.class})
public @interface EnableCache {
    
    String value() default CacheType.REDIS;
}
