import com.chenx.dao.IUserDao;
import com.chenx.model.User;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2017/3/1 0001.
 */
@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config/spring.xml")
public class TestRedis {
    @Autowired
    private IUserDao userDao;

    @Test
    public void testAddUser(){
        userDao.add(new User("123","陈祥"));
    }
}
