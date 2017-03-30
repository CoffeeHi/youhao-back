package com.chenx.service.front;

import com.chenx.gateway.web.portal.dto.TourQuery;
import com.chenx.gateway.web.portal.dto.TourRecent;
import com.chenx.model.Tour;
import com.chenx.model.TourUserState;
import com.chenx.model.dto.TourDetail;
import com.chenx.model.dto.TourTourists;
import com.chenx.utils.Page;

/**
 * Created by Administrator on 2017/3/20 0020.
 */
public interface ITourService {
    String insertTour(Tour tour, String userId);
    TourDetail getTour(String tourId, String visitUserId);
    int joinTour(TourUserState tourUserState);
    TourTourists getTourists(String tourId, String userId);
    int exitTour(String tourId, String userId);
    Page getTourPage(int pageNo, int pageSize, TourQuery tourQuery);
    TourRecent getTourRecent(String tourId);
}
