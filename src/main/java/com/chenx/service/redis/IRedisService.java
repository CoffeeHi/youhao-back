package com.chenx.service.redis;

import com.chenx.utils.dto.SessionInfo;

import java.util.concurrent.TimeUnit;

/**
 * Redis普通的String存储
 * Created by chenx
 */
public interface IRedisService {

    /**
     * 保存数据到Redis
     * @param redisKey
     * @param expireTime
     */
    void saveValue(String value, String redisKey, long expireTime);

    /**
     *
     * @param value
     * @param redisKey
     * @param timeUnit
     * @param expireTime
     */
    void saveValue(String value, String redisKey, TimeUnit timeUnit, long expireTime);

    /**
     * 从 Redis 获取随机数sessionId
     * @param redisKey
     * @return
     */
    String findValue(String redisKey);

    /**
     * 使key失效
     */
    void dealDeleteKey(String redisKey);

    /**
     * 得到用户id
     * @param sessionId
     * @return
     */
    String getSessionUserId(String sessionId);

    /**
     * 得到session
     * @param sessionId
     * @return
     */
    SessionInfo getSession(String sessionId);

}
