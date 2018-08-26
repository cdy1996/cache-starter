package com.cdy.cachestarter.configuration;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CachePuts {
    CachePut[] value() default {};
}
