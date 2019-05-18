package com.cdy.cachestarter.actuator;

import com.cdy.cache.CacheUtil;
import com.cdy.cachestarter.configuration.CacheProperties;
import com.cdy.cachestarter.core.CacheSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.cache.CacheStatistics;
import org.springframework.boot.actuate.cache.DefaultCacheStatistics;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

/**
 * todo
 * Created by 陈东一
 * 2019/5/17 0017 22:49
 */
@Slf4j
public class CustomCacheStatistucsProvider {
    
    private CacheUtil cacheUtil;
    private CacheSupport cacheSupport;
    private CacheProperties cacheProperties;
    
    
    @EventListener(ContextRefreshedEvent.class)
    public void initCacheUtil(ContextRefreshedEvent event){
        ApplicationContext source = (ApplicationContext) event.getSource();
//        this.cacheUtil = CacheFactory.getCacheUtil(source);
        this.cacheSupport = source.getBean(CacheSupport.class);
        this.cacheUtil = cacheSupport.getCacheUtil();
        this.cacheProperties = cacheSupport.getCacheProperties();
        log.info("初始化CacheSupport成功");
    }
    
    
    public CacheStatistics getCacheStatistics() {
        if (cacheUtil == null) {
            return null;
        }
        DefaultCacheStatistics statistics = new DefaultCacheStatistics();
        statistics.setSize((long) cacheUtil.size(cacheProperties.getPrefix() + "*"));
        double hitRatio = cacheSupport.getSuccess().doubleValue() / cacheSupport.getTotal().doubleValue();
        statistics.setHitRatio(hitRatio);
        statistics.setMissRatio(1 - hitRatio);
        return statistics;
    }
    
}
