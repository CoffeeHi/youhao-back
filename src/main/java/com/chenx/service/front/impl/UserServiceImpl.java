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

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Resource(name = "redisService")
    private IRedisService redisService;

    @Override
    @Transactional
    public int editUserInfo(EditUserInfo editUserInfo, String sessionId) {
        String userId = redisService.getSessionUserId(sessionId);
        editUserInfo.setId(userId);
        int num = sqlSessionTemplate.update("user.editUserInfo", editUserInfo);
        if (num == 1){
            SessionInfo sessionInfo = redisService.getSession(sessionId);
            if (StringUtils.isEmpty(sessionInfo)){
                throw new BasicRuntimeException(new CommonErrCode("401", "用户未登录或登录超时"));
            }
            sessionInfo.setUserName(editUserInfo.getName());
            redisService.saveValue(JSON.toJSONString(sessionInfo), sessionId, TimeUnit.HOURS, 1);
        }
        return num;
    }

    @Override
    public UserInfo getUserInfo(String sessionId) {
        String userId = redisService.getSessionUserId(sessionId);
        return sqlSessionTemplate.selectOne("user.getUserInfo", userId);
    }

    @Override
    public int saveImagePath(String userId, String imagePath, String sessionId) {
        Map param = new HashMap<>();
        param.put("userId", userId);
        param.put("imagePath", imagePath);
        int num = sqlSessionTemplate.update("user.updateUserImage", param);
        if (num == 1){
            SessionInfo sessionInfo = redisService.getSession(sessionId);
            if (StringUtils.isEmpty(sessionInfo)){
                throw new BasicRuntimeException(new CommonErrCode("401", "用户未登录或登录超时"));
            }
            sessionInfo.setUserImage(imagePath);
            redisService.saveValue(JSON.toJSONString(sessionInfo), sessionId, TimeUnit.HOURS, 1);
        }
        return num;
    }
}
