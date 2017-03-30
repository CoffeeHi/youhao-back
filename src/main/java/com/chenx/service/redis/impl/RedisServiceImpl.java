package com.chenx.service.redis.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chenx.gateway.commons.CommonErrCode;
import com.chenx.service.redis.IRedisService;
import com.chenx.utils.MapUtils;
import com.chenx.utils.dto.SessionInfo;
import com.fjhb.commons.dao.page.Page;
import com.fjhb.commons.dao.template.RedisDaoTemplate;
import com.fjhb.commons.dao.util.BeanUtils;
import com.fjhb.commons.exception.BasicRuntimeException;
import com.fjhb.commons.exception.ErrCodeConstant;
import com.fjhb.commons.util.BeanUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.List;
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

    @Override
    public void addList(String key, String item) {
        BoundListOperations<Serializable, Serializable> boundListOps = redisDaoTemplate.getRedisTemplate().boundListOps(key);
        try {
            boundListOps.leftPush(item.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getList(String key, int pageNo, int pageSize) {
        Page listValuePage = redisDaoTemplate.findListValueByPage(key, pageNo, pageSize);
        return (List<String>) listValuePage.getCurrentPageData();
    }



}
