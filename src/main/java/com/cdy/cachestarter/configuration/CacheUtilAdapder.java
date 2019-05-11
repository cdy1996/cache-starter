package com.cdy.cachestarter.configuration;

import com.cdy.cache.CacheUtil;
import com.cdy.redis.RedisUtil;

/**
 * redisUtil适配器
 * Created by 陈东一
 * 2019/5/11 0011 12:41
 */
public class CacheUtilAdapder implements CacheUtil {
    
    private RedisUtil redisUtil;
    
    public CacheUtilAdapder(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }
    
    @Override
    public String set(String s, String s1, int i) {
        return redisUtil.set(s,s1,i);
    }
    
    @Override
    public String get(String s) {
        return redisUtil.get(s);
    }
    
    @Override
    public void delete(String... strings) {
        redisUtil.delete(strings);
    
    }
    
    @Override
    public String set(String s, String s1) {
        return redisUtil.set(s, s1);
    }
    
    @Override
    public void expire(String s, int i) {
        redisUtil.expire(s,i);
    
    }
    
    @Override
    public boolean exist(String s) {
        return redisUtil.exist(s);
    }
}
