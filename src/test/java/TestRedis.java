import com.chenx.service.redis.IRedisService;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/3/1 0001.
 */
@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class TestRedis {

    @Resource(name = "redisService")
    private IRedisService redisService;

    @Test
    public void Test(){
        redisService.addList("12345", "hhhhhhhhhhhhhhh");
        System.out.println(redisService.getList("12345", 1, 2));
    }
}
