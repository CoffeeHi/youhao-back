package com.chenx.gateway.web.portal;

import com.chenx.gateway.commons.BasicController;
import com.chenx.model.Comment;
import com.chenx.service.front.IComService;
import com.chenx.service.redis.IRedisService;
import com.chenx.utils.Page;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
@Log4j
@RestController
@RequestMapping("com")
public class ComController extends BasicController {

    @Resource(name = "comService")
    private IComService comService;

    @RequestMapping(value = "tour/page/{pageNo}/{pageSize}/{tourId}", method = RequestMethod.GET)
    public Page getComPage (@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String tourId){
        return comService.selectComs(pageNo, pageSize, tourId);
    }

}
