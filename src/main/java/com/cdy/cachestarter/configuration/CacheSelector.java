package com.cdy.cachestarter.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import static com.cdy.cachestarter.configuration.CacheType.*;

/**
 * 用于筛选什么类型的缓存配置类
 * Created by 陈东一
 * 2018/8/19 12:17
 */
public class CacheSelector implements ImportSelector, EnvironmentAware {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
//        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableCache.class.getName());
//        String value = (String) annotationAttributes.get("value");
        String value = getTypeFromProperty().toUpperCase();
//        if (StringUtils.isNotBlank(typeFromProperty)) {
//            value = typeFromProperty;
//        }
        log.info("Cache type is {} .", value);
        switch (value) {
            case REDIS:
                return new String[]{CacheConfiguration.class.getName()};
            case MEMERCACHE:
                break;
            case EHCACHE:
                break;
            default:
                break;
        }
        return new String[0];
    }
    
    private Environment environment;
    
    private String getTypeFromProperty(){
        return environment.getProperty("cache.type");
    }
    
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
