package com.chenx.gateway.web.login;

import com.chenx.gateway.commons.BasicController;
import com.chenx.gateway.web.login.dto.ValidateCodeType;
import com.chenx.service.redis.IRedisService;
import com.chenx.utils.RandomValidateCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
@RestController
@RequestMapping("validate")
public class ValidateController{

    @Resource(name = "redisService")
    private IRedisService redisService;

    /**
     * 校验验证图片
     */
    @RequestMapping("validation/{type}")
    public boolean validation(HttpServletRequest request, String code, @PathVariable("type") int type) {
        String key = ValidateCodeType.valueOf(type).getTitle() + request.getSession().getId();
        Object value = redisService.findValue(key);
        if (value != null) {
            String sessionValidateCode = value.toString();
            if (!code.toLowerCase().equals(sessionValidateCode.toLowerCase())) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取验证码图片
     */
    @RequestMapping("getValidateImg/{type}")
    public String getValidateCode(HttpServletRequest request, HttpServletResponse response, @PathVariable("type") int type) {
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCodeUtils randomValidateCode = new RandomValidateCodeUtils();
        //redisKey = 验证码类型值 + sessionId
        String redisKey = ValidateCodeType.valueOf(type).getTitle() + request.getSession().getId();
        randomValidateCode.setRedisService(redisService);
        randomValidateCode.getRandcode(request, response, redisKey);//输出图片方法
        return redisKey;
    }
}
