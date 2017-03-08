package com.chenx.dao;

import com.chenx.model.Travel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/12/28 0028.
 */
public interface TravelRepository extends MongoRepository<Travel, String> {
    List<Travel> findByDateLessThan(Date date, Pageable page);
}
