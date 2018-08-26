package com.cdy.cachestarter.configuration;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CachePut {
    /**
     * 缓存的key
     */
    String key() default "";
    
    /**
     * todo 缓存的条件
     */
    String condition() default "";
    
    /**
     * 缓存的失效时间
     */
    int expire() default 3600;
    
    /**
     * 指明具体的类,如果是集合则写集合装的对象的类
     */
    Class<?> type();
    
    /**
     * 如果存储的是list,则设置为true
     */
    boolean isList() default false;
}
