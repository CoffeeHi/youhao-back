//package com.chenx.dao;
//
//import com.chenx.model.Tour;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by Administrator on 2016/12/28 0028.
// */
//public interface TravelRepository extends MongoRepository<Tour, String> {
//    List<Tour> findByDateLessThan(Date date, Pageable page);
//}
