package com.cdy.cachestarter.core;

import com.cdy.cache.CacheUtil;
import com.cdy.cachestarter.adapder.RedisUtilAdapder;
import com.cdy.redis.RedisUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * 缓存适配工厂
 * Created by 陈东一
 * 2019/5/11 0011 15:12
 */
public class CacheFactory {
    
    public static CacheUtil getCacheUtil(ApplicationContext bean) {
        try {
            RedisUtil bean1 = bean.getBean(RedisUtil.class);
            return new RedisUtilAdapder(bean1);
        } catch (BeansException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("");
    }

}
