import com.alibaba.fastjson.JSON;
import com.chenx.dao.TravelRepository;
import com.chenx.model.Travel;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2016/12/28 0028.
 */
@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config/spring.xml")
public class TestMongo {

    @Autowired
    private MongoOperations mongo;

    @Autowired
    private TravelRepository travelRepository;

    @Test
    public void testMongoLink(){
//        log.info(mongo.getCollection("travel").count());
//        log.info(mongo.findById("5863d41591b2fc5404835d51", Travel.class));
//        int i = 10;
//        while(i-- > 0) {
//            mongo.insert(new Travel(UUID.randomUUID().toString().replace("-", ""), new Date(), "123", "456", "789", "123",
//                    new Date(), "陈祥"));
//        }
        PageRequest page = new PageRequest(1, 6);
        log.info(JSON.toJSONString(travelRepository.findByDateLessThan(new Date(), page)));
    }
}
