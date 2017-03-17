package com.chenx.service.redis.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chenx.gateway.commons.CommonErrCode;
import com.chenx.service.redis.IRedisService;
import com.chenx.utils.MapUtils;
import com.chenx.utils.dto.SessionInfo;
import com.fjhb.commons.dao.template.RedisDaoTemplate;
import com.fjhb.commons.dao.util.BeanUtils;
import com.fjhb.commons.exception.BasicRuntimeException;
import com.fjhb.commons.exception.ErrCodeConstant;
import com.fjhb.commons.util.BeanUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis  Dao实现
 * Created by chenx
 */
@Log4j
@Service("redisService")
public class RedisServiceImpl implements IRedisService {

    @Resource
    private RedisDaoTemplate redisDaoTemplate;

    @Override
    public void saveValue(String value, String redisKey, long expireTime) {
        BoundValueOperations keyValueOperations = redisDaoTemplate.getRedisTemplate().boundValueOps(redisKey);
        keyValueOperations.set(value, expireTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public void saveValue(String value, String redisKey, TimeUnit timeUnit, long expireTime) {
        BoundValueOperations keyValueOperations = redisDaoTemplate.getRedisTemplate().boundValueOps(redisKey);
        keyValueOperations.set(value, expireTime, timeUnit);
    }

    @Override
    public String findValue(String redisKey) {
        BoundValueOperations keyValueOperations = redisDaoTemplate.getRedisTemplate().boundValueOps(redisKey);
        return (String) keyValueOperations.get();
    }

    @Override
    public void dealDeleteKey(String redisKey) {
        redisDaoTemplate.deleteRedisKey(redisKey);
    }

    @Override
    public String getSessionUserId(String redisKey) {
        SessionInfo sessionInfo = this.getSession(redisKey);
        if (StringUtils.isEmpty(sessionInfo)){
            throw new BasicRuntimeException("session已过期，请重新登录");
        }
        return sessionInfo.getUserId();
    }

    @Override
    public SessionInfo getSession(String redisKey) {
        BoundValueOperations keyValueOperations = redisDaoTemplate.getRedisTemplate().boundValueOps(redisKey);
        SessionInfo sessionInfo = JSON.parseObject((String) keyValueOperations.get(), SessionInfo.class);
        return sessionInfo;
    }

}
