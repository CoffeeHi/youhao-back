package com.chenx.service.redis.impl;

import com.chenx.service.redis.IRedisService;
import com.fjhb.commons.dao.template.RedisDaoTemplate;
import lombok.extern.log4j.Log4j;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

}
