package com.cdy.cachestarter.core;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(CacheDels.class)
public @interface CacheDel {
    
    /**
     * 缓存的key
     */
    String key() default "";
    
    /**
     * todo 缓存的条件
     */
    String condition() default "";
    
}
