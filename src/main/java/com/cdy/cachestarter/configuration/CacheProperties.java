package com.cdy.cachestarter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 缓存的配置项
 * Created by 陈东一
 * 2018/8/19 11:35
 */
@ConfigurationProperties(prefix = "cache")
public class CacheProperties {
    private String prefix = "";
    private Integer expireTime = 3600;
    
    
    public String getPrefix() {
        return prefix;
    }
    
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    public Integer getExpireTime() {
        return expireTime;
    }
    
    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }
}
    
