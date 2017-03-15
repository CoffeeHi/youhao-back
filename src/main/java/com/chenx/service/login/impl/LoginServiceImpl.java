package com.chenx.service.login.impl;

import com.chenx.YouHaoConstant;
import com.chenx.dao.dto.ValidateLoginDto;
import com.chenx.model.User;
import com.chenx.service.login.ILoginService;
import com.chenx.utils.MapUtils;
import com.chenx.utils.SHA1Util;
import com.chenx.utils.dto.SessionInfo;
import com.fjhb.commons.dao.template.RedisDaoTemplate;
import com.fjhb.commons.exception.BasicRuntimeException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

@Service("loginService")
public class LoginServiceImpl implements ILoginService {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Resource(name = "redisDaoTemplate")
    private RedisDaoTemplate redisDaoTemplate;

    @Override
    @Transactional
    public SessionInfo validateLogin(String account, String password, String sessionId) {
        password = SHA1Util.getSHA1(password);
        String userId = sqlSessionTemplate.selectOne("login.validateLogin", new ValidateLoginDto(account, password, YouHaoConstant.LOGIN_TYPE_FRONT));
        if (StringUtils.isEmpty(userId)){
            throw new BasicRuntimeException("用户名或密码错误");
        }
        User user = sqlSessionTemplate.selectOne("login.selectUserInfo", userId);
        SessionInfo sessionInfo = new SessionInfo(user.getUserId(), user.getUserName(), user.getUserImage(), YouHaoConstant.LOGIN_TYPE_FRONT);
        //设置Session过期1小时
        redisDaoTemplate.batchPutMultiHashValueWithExpire(sessionId, MapUtils.objectSeriablizeToMap(sessionInfo), 60000L * 60);
        return sessionInfo;
    }
}
