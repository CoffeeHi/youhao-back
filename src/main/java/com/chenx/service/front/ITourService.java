package com.chenx.service.front;

import com.chenx.model.Tour;
import com.chenx.model.dto.TourDetail;

/**
 * Created by Administrator on 2017/3/20 0020.
 */
public interface ITourService {
    String insertTour(Tour tour, String userId);
    TourDetail getTour(String tourId);
}
