package com.chenx.gateway.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chenx.YouHaoConstant;
import com.chenx.service.redis.IRedisService;
import com.chenx.utils.dto.SessionInfo;
import com.fjhb.commons.dao.template.RedisDaoTemplate;
import com.fjhb.commons.dao.util.StringUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 用户登录拦截器,如果无法从RedisSession中获取到User实例，将响应401状态码
 * Created by chenx.
 */
@Log4j
public class UserLoginInterceptor extends HandlerInterceptorAdapter {

    @Resource(name = "redisService")
    private IRedisService redisService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        for (Cookie ck : request.getCookies()){
            if(ck.getName().equalsIgnoreCase("userId")){
                SessionInfo session = redisService.getSession(request.getRequestedSessionId());
                if (StringUtils.isEmpty(session))
                    break;
                if (ck.getValue().equals(session.getUserId()) &&
                         session.getUserType() == YouHaoConstant.LOGIN_TYPE_FRONT){
                    return true;
                }
                break;
            }
        }
        log.info("用户未登录,当前sessionId为--"+request.getSession().getId());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return false;
    }
}
