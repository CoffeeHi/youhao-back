package com.chenx.gateway.web.portal;

import com.alibaba.fastjson.JSON;
import com.chenx.gateway.commons.BasicController;
import com.chenx.gateway.web.portal.dto.TourQuery;
import com.chenx.gateway.web.portal.dto.TourRecent;
import com.chenx.model.Tour;
import com.chenx.model.dto.TourDetail;
import com.chenx.model.dto.TourSimple;
import com.chenx.service.front.ITourService;
import com.chenx.utils.Page;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.ws.rs.Path;
import java.util.List;

/**
 * Created by Administrator on 2017/1/1 0001.
 */
@Log4j
@RestController
@RequestMapping("tour")
public class TourController extends BasicController {

    @Resource(name = "tourService")
    private ITourService tourService;



    @RequestMapping(value = "page/{pageNo}/{pageSize}", method = RequestMethod.GET)
    public Page getTourPage(@PathVariable int pageNo, @PathVariable int pageSize){
        TourQuery tourQuery = super.getRequestParam(TourQuery.class);
        return tourService.getTourPage(pageNo, pageSize, tourQuery);
    }

    @RequestMapping(value = "recent/{tourId}", method = RequestMethod.GET)
    public TourRecent getTourRecent(@PathVariable String tourId){
        return tourService.getTourRecent(tourId);
    }
}
