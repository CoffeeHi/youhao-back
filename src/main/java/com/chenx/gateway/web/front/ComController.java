package com.chenx.gateway.web.front;

import com.chenx.gateway.commons.BasicController;
import com.chenx.model.Comment;
import com.chenx.model.dto.ComList;
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

    @Resource(name = "redisService")
    private IRedisService redisService;

    @Resource(name = "comService")
    private IComService comService;

    @RequestMapping(value = "tour", method = RequestMethod.POST)
    public int insertCom(@RequestBody Comment comment){
        comment.setComAuthor(redisService.getSessionUserId(request.getRequestedSessionId()));
        comment.setComTime(new Date());
        return comService.insertCom(comment);
    }

    @RequestMapping(value = "tour/{comId}", method = RequestMethod.DELETE)
    public int deleteCom(@PathVariable String comId){
        String userId = redisService.getSessionUserId(request.getRequestedSessionId());
        return comService.deleteCom(comId, userId);
    }

}
