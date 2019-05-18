package com.cdy.cachestarter.adapder;

import java.util.concurrent.ConcurrentHashMap;

/**
 * redisUtil适配器
 * Created by 陈东一
 * 2019/5/11 0011 12:41
 */
public class MapAdapder extends CacheUtilAdapder<ConcurrentHashMap<String, String>> {
    
    private ConcurrentHashMap<String, String> map;
    
    @Override
    public String set(String key, String value, int expire) {
        return map.put(key, value);
    }
    
    @Override
    public String get(String key) {
        return map.get(key);
    }
    
    @Override
    public void delete(String... keys) {
        for (String key : keys) {
            map.remove(key);
        }
    }
    
    @Override
    public String set(String key, String value) {
        return map.put(key, value);
    }
    
    @Override
    public void expire(String key, int expire) {
    }
    
    @Override
    public boolean exist(String key) {
        return map.containsKey(key);
    }
    
    @Override
    public int size(String prefix) {
        return map.size();
    }
    
}
