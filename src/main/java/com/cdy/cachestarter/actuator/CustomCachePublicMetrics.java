package com.cdy.cachestarter.actuator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.cache.CacheStatistics;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;

import java.util.Collection;

/**
 * todo
 * Created by 陈东一
 * 2019/5/17 0017 22:23
 */
@Slf4j
public class CustomCachePublicMetrics implements PublicMetrics {
    
    @Autowired
    CustomCacheStatistucsProvider customCacheStatistucsProvider;
    
    
    @Override
    public Collection<Metric<?>> metrics() {
        CacheStatistics cacheStatistics = customCacheStatistucsProvider.getCacheStatistics();
        Collection<Metric<?>> metrics = cacheStatistics.toMetrics("cache.");
        for (Metric<?> metric : metrics) {
            log.info(metric.toString());
        }
        return metrics;
    }
}
