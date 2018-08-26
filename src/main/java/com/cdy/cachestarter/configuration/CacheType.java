package com.cdy.cachestarter.configuration;

/**
 * 缓存实现常量
 * Created by 陈东一
 * 2018/8/19 12:16
 */
public interface CacheType {
    //redis作缓存
    String REDIS = "REDIS";
    //ehcache作缓存
    String EHCACHE = "EHCACHE";
    //memercache作缓存
    String MEMERCACHE = "MEMERCACHE";
}
