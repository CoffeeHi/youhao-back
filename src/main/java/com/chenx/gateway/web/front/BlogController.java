package com.chenx.gateway.web.front;

import com.chenx.gateway.commons.BasicController;
import com.chenx.model.Blog;
import com.chenx.service.front.IBlogService;
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

/**
 * Created by Administrator on 2017/3/28 0028.
 */
@Log4j
@RestController
@RequestMapping("blog")
public class BlogController extends BasicController {

    @Resource(name = "redisService")
    private IRedisService redisService;

    @Resource(name = "blogService")
    private IBlogService blogService;

    @RequestMapping(method = RequestMethod.POST)
    public String insert(@RequestBody Blog blog){
        String userId = redisService.getSessionUserId(request.getRequestedSessionId());
        blog.setId(UUIDUtils.getUUID());
        blog.setAuthor(userId);
        blog.setPostTime(new Date());
        return blogService.insertBlog(blog);
    }

    @RequestMapping(value = "upload/blogImage", method = RequestMethod.POST)
    public String saveTourImage(MultipartFile image){
        log.info("-------------上传文章图片开始------------");
        log.info("当前操作系统为:" + System.getProperty("os.name"));
        String dir = "upload/blog/" + redisService.getSessionUserId(request.getRequestedSessionId()) + "/";
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
        log.info("-------------上传文章图片结束------------");
        return savePath;
    }
}
