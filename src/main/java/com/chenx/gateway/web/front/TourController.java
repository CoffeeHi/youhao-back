package com.chenx.gateway.web.front;

import com.chenx.YouHaoConstant;
import com.chenx.gateway.commons.BasicController;
import com.chenx.model.Tour;
import com.chenx.model.TourUserState;
import com.chenx.model.dto.TourDetail;
import com.chenx.model.dto.TourTourists;
import com.chenx.model.dto.TourUser;
import com.chenx.service.front.ITourService;
import com.chenx.service.redis.IRedisService;
import com.chenx.utils.UUIDUtils;
import com.fjhb.commons.exception.BasicRuntimeException;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20 0020.
 */
@Log4j
@RestController
@RequestMapping("tour")
public class TourController extends BasicController {

    @Resource(name = "redisService")
    private IRedisService redisService;

    @Resource(name = "tourService")
    private ITourService tourService;

    @RequestMapping(method = RequestMethod.POST)
    public String insert(@RequestBody Tour tour){ //返回旅单id
        String userId = redisService.getSessionUserId(request.getRequestedSessionId());
        return tourService.insertTour(tour, userId);
    }

    @RequestMapping(value = "upload/tourImage/{imgType}", method = RequestMethod.POST)
    public String saveTourImage(MultipartFile image, @PathVariable int imgType){
        log.info("-----上传旅单" + (imgType==YouHaoConstant.TOUR_IMAGE?"说明":"封面") + "图片开始-----");
        log.info("当前操作系统为:" + System.getProperty("os.name"));
        String dir = (imgType == YouHaoConstant.TOUR_IMAGE ? "upload/tour/images/" : "upload/tour/covers/")
                + redisService.getSessionUserId(request.getRequestedSessionId()) + "/";
        String path = null;
        if (System.getProperty("os.name").indexOf("Window") != -1){
            path = request.getSession().getServletContext().getRealPath(dir);
        }else {

        }
        //判断文件的MIMEtype
        String type = image.getContentType();
        if(type==null || !type.toLowerCase().startsWith("image/")) throw new BasicRuntimeException("不支持的文件类型，仅支持图片！");
        String oldName = image.getOriginalFilename();
        String newName = UUIDUtils.getUUID() + oldName.substring(oldName.lastIndexOf('.'));
        String savePath = dir + newName;
        log.info("上传图片类型:" + type);
        log.info("上传图片路径:" + path + "\\" + newName);
        log.info("保存图片路径:" + savePath);

        File imageFile = new File(path, newName);
        try{
            File checkDir = new File(path);
            if(!checkDir.exists()) {
                checkDir.mkdirs();
            }
            image.transferTo(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BasicRuntimeException("图片上传错误，请重试");
        }
        log.info("-----上传旅单" + (imgType==YouHaoConstant.TOUR_IMAGE?"说明":"封面") + "图片结束-----");
        return savePath;
    }

    @RequestMapping(value = "{tourId}", method = RequestMethod.GET)
    public TourDetail get(@PathVariable String tourId){
        return tourService.getTour(tourId);
    }

    @RequestMapping(value = "{tourId}/role/{role}", method = RequestMethod.POST)
    public int join(@PathVariable String tourId, @PathVariable int role){ //返回用户加入的角色
        String userId = redisService.getSessionUserId(request.getRequestedSessionId());
        TourUserState tourUserState = new TourUserState();
        tourUserState.setTourId(tourId);
        tourUserState.setUserRole(role);
        tourUserState.setUserId(userId);
        tourUserState.setUserState(YouHaoConstant.TOUR_USER_STATE_JOIN);
        tourUserState.setUserJoinTime(new Date());
        return tourService.joinTour(tourUserState);
    }

    @RequestMapping(value = "{tourId}/tourists", method = RequestMethod.GET)
    public TourTourists getTourists(@PathVariable String tourId){
        String userId = redisService.getSessionUserId(request.getRequestedSessionId());
        return tourService.getTourists(tourId, userId);
    }

    @RequestMapping(value = "{tourId}", method = RequestMethod.DELETE)
    public int exitTour(@PathVariable String tourId){
        String userId = redisService.getSessionUserId(request.getRequestedSessionId());
        return tourService.exitTour(tourId, userId);
    }
}
