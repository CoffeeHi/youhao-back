package com.chenx.service.login.impl;

import com.alibaba.fastjson.JSON;
import com.chenx.YouHaoConstant;
import com.chenx.dao.dto.ValidateLoginDto;
import com.chenx.gateway.commons.CommonErrCode;
import com.chenx.model.User;
import com.chenx.service.login.ILoginService;
import com.chenx.service.redis.IRedisService;
import com.chenx.utils.MapUtils;
import com.chenx.utils.SHA1Util;
import com.chenx.utils.UUIDUtils;
import com.chenx.utils.dto.SessionInfo;
import com.fjhb.commons.dao.template.RedisDaoTemplate;
import com.fjhb.commons.exception.BasicRuntimeException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

@Service("loginService")
public class LoginServiceImpl implements ILoginService {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Resource(name = "redisDaoTemplate")
    private RedisDaoTemplate redisDaoTemplate;

    @Resource(name = "redisService")
    private IRedisService redisService;

    @Override
    @Transactional
    public SessionInfo validateLogin(String account, String password, String sessionId) {
        password = SHA1Util.getSHA1(password);
        String userId = sqlSessionTemplate.selectOne("login.validateLogin", new ValidateLoginDto(account, password, YouHaoConstant.LOGIN_TYPE_FRONT));
        if (StringUtils.isEmpty(userId)){
            throw new BasicRuntimeException("用户名或密码错误");
        }
        User user = sqlSessionTemplate.selectOne("login.selectUserInfo", userId);
        SessionInfo sessionInfo = new SessionInfo(user.getUserId(), user.getUserName(),
                user.getUserImage(), YouHaoConstant.LOGIN_TYPE_FRONT);
        //设置Session过期1小时
        redisService.saveValue(JSON.toJSONString(sessionInfo), sessionId, TimeUnit.HOURS, 1);
        return sessionInfo;
    }

    @Override
    public SessionInfo checkLogin(String userId, String sessionId) {
        SessionInfo session = redisService.getSession(sessionId);
        if (!StringUtils.isEmpty(session) && session.getUserId().equals(userId) &&
                session.getUserType() == YouHaoConstant.LOGIN_TYPE_FRONT)
            return session;
        else
            throw new BasicRuntimeException(new CommonErrCode("401", "用户未登录或登录超时"));
    }
}
