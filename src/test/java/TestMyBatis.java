import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/3/1 0001.
 */
@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring.xml"})
@Log4j
public class TestMyBatis {

    @Resource
    SqlSessionTemplate sqlSessionTemplate;

    @Test
    public void Test(){
        log.info(sqlSessionTemplate.toString());
    }
}
