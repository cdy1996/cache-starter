package com.cdy.cachestarter.configuration;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheDels{
    
    CacheDel[] value() default {};
}
