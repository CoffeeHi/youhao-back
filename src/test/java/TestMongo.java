//import com.chenx.dao.TravelRepository;
import com.alibaba.fastjson.JSON;
import com.chenx.model.Tour;
import com.chenx.model.dto.Author;
import com.chenx.service.front.ITourService;
import com.chenx.utils.UUIDUtils;
import com.fjhb.commons.dao.page.Page;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/12/28 0028.
 */
@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring/spring-data-mongodb.xml","classpath:spring/spring-annotation.xml",
//        "classpath:spring/spring-sys-properties.xml"})
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class TestMongo {

    @Autowired
    private MongoOperations mongo;

//    @Autowired
//    private TravelRepository travelRepository;


    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    ITourService tourService;

    @Test
    public void testMongoLink(){
//        Tour t = new Tour();
//        t.setId(UUIDUtils.getUUID());
//        t.setDateStart(new Date());
//        t.setDateOver(new Date());
//        System.out.println(tourService.insertTour(t, "C54429D6383118AFD8DAE375340E92D2"));

//        String tourId = "580ddb43430a453c89ab34611f1d6841";
//        tourService.getTour(tourId);

        List<Tour> tourList = mongo.find(new Query().skip(0).limit(10).with(new Sort(new Sort.Order(Sort.Direction.DESC, "postTime"))), Tour.class);
        List<String> authorIds = tourList.stream().map(i -> i.getAuthor()).collect(Collectors.toList());
        List<Author> authors = sqlSessionTemplate.selectList("tour.getTourAuthors", authorIds);
        System.out.println(JSON.toJSONString(authors));

//        log.info(mongo.getCollection("travel").count());
//        log.info(mongo.findById("5863d41591b2fc5404835d51", Tour.class));
//        int i = 10;
//        while(i-- > 0) {
//        }
//        PageRequest page = new PageRequest(1, 6);
//        log.info(JSON.toJSONString(travelRepository.findByDateLessThan(new Date(), page)));
    }
}
