package com.chenx.service.impl;

import com.chenx.dao.TravelRepository;
import com.chenx.model.Travel;
import com.chenx.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/1/1 0001.
 */
@Service("indexService")
public class IndexServiceImpl implements IndexService {

    @Autowired
    private TravelRepository travelRepository;

    @Override
    public List<Travel> findTravelList(long timestamp) {
//        PageRequest page = new PageRequest(1, 20);
//        return travelRepository.findByDateLessThan(new Date(), new Sort(Sort.Direction.DESC, "created"), page);
        return null;
    }
}
