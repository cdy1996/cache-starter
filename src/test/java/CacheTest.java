import com.cdy.cachestarter.configuration.EnableCache;
import com.cdy.redis.RedisUtil;
import com.cdy.redisstarter.config.EnableRedisUtil;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.Jedis;

/**
 * 单元测试
 * Created by 陈东一
 * 2019/5/11 0011 12:15
 */

@PropertySource("classpath:application.properties")
@EnableCache
@EnableRedisUtil
@Configuration
public class CacheTest {
    
    
    @Test
    public void test(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(CacheTest.class);
        RedisUtil bean = applicationContext.getBean(RedisUtil.class);
        Jedis jedis = bean.getJedis();
        System.out.println(jedis.info());
        jedis.close();
    }
}
