package com.chenx.service.redis;

/**
 * Redis存储
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
     * 从 Redis 获取随机数sessionId
     * @param redisKey
     * @return
     */
    String findValue(String redisKey);

    /**
     * 使key失效
     */
    void dealDeleteKey(String redisKey);
}
