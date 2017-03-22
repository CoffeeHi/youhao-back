package com.chenx.gateway.web.portal;

import com.chenx.gateway.commons.BasicController;
import com.chenx.model.Tour;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Administrator on 2017/1/1 0001.
 */
@Log4j
@Controller
public class IndexController extends BasicController {

    @RequestMapping(value = "index/{timestamp}", method = RequestMethod.GET)
    public List<Tour> findTravelList(@PathVariable long timestamp){
        return null;
    }

}
