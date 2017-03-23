//import com.chenx.dao.TravelRepository;
import com.chenx.model.Tour;
import com.chenx.service.front.ITourService;
import com.chenx.utils.UUIDUtils;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by Administrator on 2016/12/28 0028.
 */
@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring/spring-data-mongodb.xml","classpath:spring/spring-annotation.xml",
//        "classpath:spring/spring-sys-properties.xml"})
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class TestMongo {

//    @Autowired
//    private MongoOperations mongo;

//    @Autowired
//    private TravelRepository travelRepository;

    @Autowired
    ITourService tourService;

    @Test
    public void testMongoLink(){
//        Tour t = new Tour();
//        t.setId(UUIDUtils.getUUID());
//        t.setDateStart(new Date());
//        t.setDateOver(new Date());
//        System.out.println(tourService.insertTour(t, "C54429D6383118AFD8DAE375340E92D2"));

        String tourId = "580ddb43430a453c89ab34611f1d6841";
        tourService.getTour(tourId);

//        log.info(mongo.getCollection("travel").count());
//        log.info(mongo.findById("5863d41591b2fc5404835d51", Tour.class));
//        int i = 10;
//        while(i-- > 0) {
//        }
//        PageRequest page = new PageRequest(1, 6);
//        log.info(JSON.toJSONString(travelRepository.findByDateLessThan(new Date(), page)));
    }
}
