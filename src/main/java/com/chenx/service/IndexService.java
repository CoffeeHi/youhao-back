package com.chenx.service;

import com.chenx.model.Tour;

import java.util.List;

/**
 * Created by Administrator on 2017/1/1 0001.
 */
public interface IndexService {
    List<Tour> findTravelList(long timestamp);
}
