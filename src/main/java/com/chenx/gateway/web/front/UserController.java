package com.chenx.gateway.web.front;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chenx.gateway.commons.BasicController;
import com.chenx.model.UserInfo;
import com.chenx.model.dto.EditUserInfo;
import com.chenx.service.front.IUserService;
import com.chenx.service.redis.IRedisService;
import com.chenx.utils.ImageCut;
import com.chenx.utils.UUIDUtils;
import com.fjhb.commons.exception.BasicRuntimeException;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
@Log4j
@RestController
@RequestMapping("user")
public class UserController extends BasicController{

    @Resource(name = "userService")
    private IUserService userService;

    @Resource(name = "redisService")
    private IRedisService redisService;

    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    public int edit(@RequestBody EditUserInfo editUserInfo){
        return userService.editUserInfo(editUserInfo, request.getRequestedSessionId());
    }

    @RequestMapping(value = "upload/userImage", method = RequestMethod.POST)
    public String uploadUserImage(MultipartFile avatar_file, String avatar_src, String avatar_data){
        log.info("当前操作系统为:" + System.getProperty("os.name"));
        String dir = "upload/user/images/" + redisService.getSessionUserId(request.getRequestedSessionId()) + "/";
        String path = null;
        if (System.getProperty("os.name").indexOf("Window") != -1){
            path = request.getSession().getServletContext().getRealPath(dir);
        }else {

        }

        String name = avatar_file.getOriginalFilename();
        //判断文件的MIMEtype
        String type = avatar_file.getContentType();
        if(type==null || !type.toLowerCase().startsWith("image/")) throw new BasicRuntimeException("不支持的文件类型，仅支持图片！");
        String fileName = UUIDUtils.getUUID() + name.substring(name.lastIndexOf('.'));
        String savePath = dir + fileName;
        log.info("上传头像类型:"+type);
        log.info("上传头像路径:"+path+":"+fileName);
        log.info("保存头像路径:"+savePath+":"+savePath);

        JSONObject joData = (JSONObject) JSONObject.parse(avatar_data);
        // 用户经过剪辑后的图片的大小
        float x = joData.getFloatValue("x");
        float y = joData.getFloatValue("y");
        float w =  joData.getFloatValue("width");
        float h =  joData.getFloatValue("height");

        //开始上传
        File targetFile = new File(path, fileName);
        //保存
        try {
            if(!targetFile.exists()){
                targetFile.mkdirs();
                InputStream is = avatar_file.getInputStream();
                ImageCut.cut(is, targetFile, (int)x,(int)y,(int)w,(int)h);
                userService.saveImagePath(redisService.getSessionUserId(request.getRequestedSessionId()),
                        savePath, request.getRequestedSessionId());
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BasicRuntimeException("上传失败，出现异常");
        }
        return request.getSession().getServletContext().getContextPath()+dir+fileName;
    }

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public UserInfo detail(){
        return userService.getUserInfo(request.getRequestedSessionId());
    }
}
