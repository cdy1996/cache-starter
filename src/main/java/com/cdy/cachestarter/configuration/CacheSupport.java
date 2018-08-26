package com.cdy.cachestarter.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * 缓存的辅助类
 * Created by 陈东一
 * 2018/8/25 21:08
 */
public class CacheSupport {
    private static final Logger log = LoggerFactory.getLogger(CacheSupport.class);
    
    /**
     * 解析缓存的key
     * @param key 注解上的key
     * @param method  对应的方法
     * @param args 对应方法的参数
     * @param prefix 缓存的key前缀
     * @return
     */
    public static String parseKey(String key, Method method, Object[] args, String prefix) {
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        ExpressionParser parser = new SpelExpressionParser();
        
        ParameterNameDiscoverer parameterNameDiscoverer =
                new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        
        for (int i = 0; i < parameterNames.length; i++) {
            evaluationContext.setVariable(parameterNames[i], args[i]);
        }
        String cacheKey = parser.parseExpression(prefix + key, new TemplateParserContext())
                .getValue(evaluationContext, String.class);
        log.info("cache key is {}", cacheKey);
        return cacheKey;
        
    }
}
