package com.cdy.cachestarter.configuration;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(CachePuts.class)
public @interface CachePut {
    /**
     * 缓存的key
     */
    String key() default "";
    
    /**
     * todo 缓存的条件
     */
//    String condition() default "";
    
    /**
     * 缓存的失效时间
     */
    int expire() default 3600;
    
    /**
     * 如果是集合则写集合装的对象的类
     */
    Class<?> type() default Object.class;
    
}
