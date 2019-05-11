package com.cdy.cachestarter.configuration;

import com.cdy.cache.CacheUtil;
import com.cdy.redis.RedisUtil;

/**
 * 缓存适配工厂
 * Created by 陈东一
 * 2019/5/11 0011 15:12
 */
public class CacheFactory {
    
    public static CacheUtil getCacheUtil(RedisUtil bean) {
        return new CacheUtilAdapder(bean);
    }

}
