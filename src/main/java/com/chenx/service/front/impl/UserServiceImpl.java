package com.chenx.service.front.impl;

import com.alibaba.fastjson.JSON;
import com.chenx.gateway.commons.CommonErrCode;
import com.chenx.model.UserInfo;
import com.chenx.model.dto.EditUserInfo;
import com.chenx.service.front.IUserService;
import com.chenx.service.redis.IRedisService;
import com.chenx.utils.dto.SessionInfo;
import com.fjhb.commons.exception.BasicRuntimeException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/3/16 0016.
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Resource(name = "redisService")
    private IRedisService redisService;

    @Override
    @Transactional
    public int editUserInfo(EditUserInfo editUserInfo, SessionInfo sessionInfo) {
        int num = sqlSessionTemplate.update("user.editUserInfo", editUserInfo);
        if (num == 1){
            if (StringUtils.isEmpty(sessionInfo)){
                throw new BasicRuntimeException(new CommonErrCode("401", "用户未登录或登录超时"));
            }
            sessionInfo.setUserName(editUserInfo.getName());
            redisService.saveValue(JSON.toJSONString(sessionInfo), sessionInfo.getSessionId(), TimeUnit.HOURS, 1);
        }
        return num;
    }

    @Override
    public UserInfo getUserInfo(String userId) {
        return sqlSessionTemplate.selectOne("user.getUserInfo", userId);
    }

    @Override
    @Transactional
    public int saveImagePath(SessionInfo sessionInfo, String imagePath) {
        Map param = new HashMap<>();
        param.put("userId", sessionInfo.getUserId());
        param.put("imagePath", imagePath);
        int num = sqlSessionTemplate.update("user.updateUserImage", param);
        sessionInfo.setUserImage(imagePath);
        redisService.saveValue(JSON.toJSONString(sessionInfo), sessionInfo.getSessionId(), TimeUnit.HOURS, 1);
        return num;
    }
}
