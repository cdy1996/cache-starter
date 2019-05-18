package com.cdy.cachestarter.actuator;

import org.springframework.boot.actuate.autoconfigure.PublicMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * todo
 * Created by 陈东一
 * 2019/5/17 0017 22:23
 */
@AutoConfigureBefore(PublicMetricsAutoConfiguration.class)
@Configuration
@ConditionalOnProperty(value = "cache.metric",havingValue = "true")
public class CustomCachePublicMetricsConfiguration {
    
    @Bean
    public CustomCacheStatistucsProvider customCacheStatistucsProvider(){
        return new CustomCacheStatistucsProvider();
    }
    
    @Bean
    public CustomCachePublicMetrics customCachePublicMetrics(){
        return new CustomCachePublicMetrics();
    }

}
