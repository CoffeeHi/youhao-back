package com.chenx.service.front.impl;

import com.chenx.YouHaoConstant;
import com.chenx.gateway.web.portal.dto.TourQuery;
import com.chenx.model.Tour;
import com.chenx.model.TourState;
import com.chenx.model.TourUserState;
import com.chenx.model.UserInfo;
import com.chenx.model.dto.*;
import com.chenx.service.front.ITourService;
import com.chenx.service.redis.IRedisService;
import com.chenx.utils.Page;
import com.chenx.utils.UUIDUtils;
import com.fjhb.commons.exception.BasicRuntimeException;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
@Log4j
@Service("tourService")
public class TourServiceImpl implements ITourService {

    @Autowired
    private MongoOperations mongo;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

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
        mongo.save(tour);
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

    @Override
    public TourDetail getTour(String tourId) {
        Tour tour = mongo.findById(tourId, Tour.class);
        if (StringUtils.isEmpty(tour)){
            throw new BasicRuntimeException("该旅单不存在");
        }
        TourDetail tourDetail = new TourDetail();
        BeanUtils.copyProperties(tour, tourDetail);

        TourState tourState = sqlSessionTemplate.selectOne("tour.getTourState", tourId);
        BeanUtils.copyProperties(tourState, tourDetail);

        UserInfo userInfo = sqlSessionTemplate.selectOne("user.getUserInfo", tour.getAuthor());
        tourDetail.setAuthorImage(userInfo.getImage());
        tourDetail.setAuthorName(userInfo.getName());
        tourDetail.setAuthorIntro(userInfo.getIntro());
        return tourDetail;
    }

    @Override
    @Transactional
    public int joinTour(TourUserState tourUserState) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("tourId", tourUserState.getTourId());
        paramMap.put("userId", tourUserState.getUserId());
        List<String> tourList = sqlSessionTemplate.selectList("tour.checkTourUser", paramMap);
        if (tourList != null && tourList.size() > 0){
            throw new BasicRuntimeException("你已经加入过该旅单");
        }
        sqlSessionTemplate.insert("tour.joinTour", tourUserState);
        return tourUserState.getUserRole();
    }

    @Override
    public TourTourists getTourists(String tourId, String userId) {
        TourTourists tourTourists = new TourTourists();
        List<TourUser> tourUsers = sqlSessionTemplate.selectList("tour.getTourists", tourId);
        tourTourists.setTourUsers(tourUsers);
        tourTourists.setIfJoin(false);
        for (TourUser tu : tourUsers){
            if (tu.getUserId().equals(userId)){
                tourTourists.setIfJoin(true);
                break;
            }
        }
        return tourTourists;
    }

    @Override
    public int exitTour(String tourId, String userId) {
        Map param = new HashMap<>();
        param.put("userId" , userId);
        param.put("tourId" , tourId);
        return sqlSessionTemplate.delete("tour.exitTour", param);
    }

    @Override
    public Page getTourPage(int pageNo, int pageSize, TourQuery tourQuery) {
        log.info("--------mongo1--" + System.currentTimeMillis());
//        List<Tour> tourList = mongo.find(new Query().skip(pageSize * (pageNo-1)).limit(pageSize).with(new Sort(new Sort.Order(Sort.Direction.DESC, "postTime"))), Tour.class);
        Query mongoQuery = new Query();
        if (!StringUtils.isEmpty(tourQuery.getTarget())){
            mongoQuery.addCriteria(Criteria.where("target").regex("^.*" + tourQuery.getTarget() + ".*$"));
        }
        if (!StringUtils.isEmpty(tourQuery.getDateStart())){
            mongoQuery.addCriteria(Criteria.where("dateStart").gte(tourQuery.getDateStart()));
        }
        if (!StringUtils.isEmpty(tourQuery.getDateOver())){
            mongoQuery.addCriteria(Criteria.where("dateOver").lte(tourQuery.getDateOver()));
        }
        List<Tour> tourList = mongo.find(mongoQuery.skip(pageSize * (pageNo-1)).limit(pageSize).with(new Sort(new Sort.Order(Sort.Direction.DESC, "postTime"))), Tour.class);
        log.info("--------mongo2--" + System.currentTimeMillis());

        Page page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        if(tourList.size() >0){
            List<String> authorIds = tourList.stream().map(i -> i.getAuthor()).collect(Collectors.toList());
            log.info("--------mysql1--" + System.currentTimeMillis());
            List<Author> authors = sqlSessionTemplate.selectList("tour.getTourAuthors", authorIds);
            log.info("--------mysql2--" + System.currentTimeMillis());
            Map<String, Author> authorMap = authors.stream().collect(Collectors.toMap(Author::getUserId, (p) -> p));
            log.info("--------mapping1--" + System.currentTimeMillis());
            List<TourSimple> ts = new ArrayList<>();
            for (Tour t : tourList){
                TourSimple tsTmp = new TourSimple();
                BeanUtils.copyProperties(t, tsTmp);
                Author a = authorMap.get(t.getAuthor());
                BeanUtils.copyProperties(a, tsTmp);
                ts.add(tsTmp);
            }
            log.info("--------mapping2--" + System.currentTimeMillis());
            page.setCurrentPageData(ts);
            log.info("--------mongo Total_1--" + System.currentTimeMillis());
            page.setTotalSize(mongo.count(new Query(), Tour.class));
            log.info("--------mongo Total_2--" + System.currentTimeMillis());
        }else {
            page.setTotalSize(0);
        }
        return page;
    }
}
