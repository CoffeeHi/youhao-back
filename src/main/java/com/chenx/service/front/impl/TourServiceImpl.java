package com.chenx.service.front.impl;

import com.chenx.YouHaoConstant;
import com.chenx.model.Tour;
import com.chenx.model.TourState;
import com.chenx.model.TourUserState;
import com.chenx.service.front.ITourService;
import com.chenx.service.redis.IRedisService;
import com.chenx.utils.UUIDUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
@Service("tourService")
public class TourServiceImpl implements ITourService {

    @Autowired
    private MongoOperations mongo;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Resource(name = "redisService")
    private IRedisService redisService;

//    在单独使用不带任何参数的 @Transactional 注释时，传播模式要设置为 REQUIRED，
// 只读标志设置为 false，事务隔离级别设置为 READ_COMMITTED，
// 而且事务不会针对受控异常（checked exception）回滚
    @Override
    @Transactional
    public String insertTour(Tour tour, String userId) {
        String tourId = UUIDUtils.getUUID();
        Date postTime = new Date();
        //新建旅单
        tour.setId(tourId);
        tour.setPostTime(postTime);
        tour.setAuthor(userId);
        mongo.save(tour, "tour");
        //记录旅单状态
        TourState ts = new TourState();
        ts.setTourId(tourId);
        sqlSessionTemplate.insert("tour.initTourState", ts);
        //记录用户状态
        TourUserState tus = new TourUserState();
        tus.setTourId(tourId);
        tus.setUserId(userId);
        tus.setUserJoinTime(postTime);
        tus.setUserRole(YouHaoConstant.TOUR_USER_ROLE_AUTHOR);
        tus.setUserState(YouHaoConstant.TOUR_USER_STATE_JOIN);
        sqlSessionTemplate.insert("tour.initTourUserState", tus);
        return tourId;
    }
}
