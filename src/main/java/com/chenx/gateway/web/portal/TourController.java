package com.chenx.gateway.web.portal;

import com.chenx.gateway.commons.BasicController;
import com.chenx.model.Tour;
import com.chenx.model.dto.TourDetail;
import com.chenx.service.front.ITourService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

    @RequestMapping(value = "{tourId}", method = RequestMethod.GET)
    public TourDetail get(@PathVariable String tourId){
        return tourService.getTour(tourId);
    }
}
