package com.minlia.modules.security.model.token;

import com.minlia.module.redis.util.RedisUtils;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/7/11 11:09 AM
 */
public class TokenCacheUtils {

    public static final String TOKEN = "token:";

    public static final String TOKEN_R = "token.r:";

    private static String getKey(String guid) {
        return TOKEN + guid;
    }

    /**
     * 缓存token
     *
     * @param guid
     * @param token
     * @param expirationTime
     */
    public static void cache(String guid, String token, int expirationTime) {
        RedisUtils.set(getKey(guid), token, expirationTime);
    }

    /**
     * 获取token
     *
     * @param guid
     */
    public static Object get(String guid) {
        return RedisUtils.get(getKey(guid));
    }

    /**
     * 杀掉token
     *
     * @param guid
     */
    public static void kill(String guid) {
        RedisUtils.del(getKey(guid));
    }

    /**
     * 是否存在
     *
     * @param guid
     * @return
     */
    public static boolean exists(String guid) {
        return RedisUtils.getRedisTemplate().hasKey(getKey(guid));
    }

    /**
     * 续期
     *
     * @param guid
     * @return
     */
    public static boolean expire(String guid, long time) {
        return RedisUtils.expire(getKey(guid), time);
    }

}
