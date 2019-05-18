package com.cdy.cachestarter.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 缓存的配置项
 * Created by 陈东一
 * 2018/8/19 11:35
 */
@Data
@ConfigurationProperties(prefix = "cache")
public class CacheProperties {
    private String prefix = "";
    private Integer expireTime = 3600;
    private String type = "redis";
    private String enable = "true";
    
}
    
