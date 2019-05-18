package com.cdy.cachestarter.adapder;

import com.cdy.redis.RedisUtil;

/**
 * redisUtil适配器
 * Created by 陈东一
 * 2019/5/11 0011 12:41
 */
public class RedisUtilAdapder extends CacheUtilAdapder<RedisUtil> {
    
    private RedisUtil redisUtil;
    
    public RedisUtilAdapder(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }
    
    @Override
    public String set(String key, String value, int expire) {
        return redisUtil.set(key, value, expire);
    }
    
    @Override
    public String get(String key) {
        return redisUtil.get(key);
    }
    
    @Override
    public void delete(String... keys) {
        redisUtil.delete(keys);
    }
    
    @Override
    public String set(String key, String value) {
        return redisUtil.set(key, value);
    }
    
    @Override
    public void expire(String key, int expire) {
        redisUtil.expire(key, expire);
    }
    
    @Override
    public boolean exist(String key) {
        return redisUtil.exist(key);
    }
    
    @Override
    public int size(String prefix) {
        return redisUtil.size(prefix);
    }
    
}
