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
//    public static final String TOKEN_GUID_SESSION = "token:%s_%s";
//
//    private static String getKeyWithSessionId(String key) {
//        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest servletRequest = servletRequestAttributes.getRequest();
//        Object sid = servletRequest.getSession().getAttribute(SecurityConstant.SID);
//        return String.format(TOKEN_GUID_SESSION, key, sid);
//    }

    /**
     * 获取token
     *
     * @param key
     */
    public static Object get(String key) {
        return RedisUtils.get(getKey(key));
    }

    private static String getKey(String key) {
        return TOKEN + key;
    }

    /**
     * 缓存token
     *
     * @param key
     * @param token
     * @param expirationTime
     */
    public static void cache(String key, String token, int expirationTime) {
        RedisUtils.set(getKey(key), token, expirationTime);
    }


    /**
     * 杀掉token
     *
     * @param key
     */
    public static void kill(String key) {
        RedisUtils.del(getKey(key));
    }

    /**
     * 杀掉token
     *
     * @param uid
     */
    public static void kill(Long uid) {
        RedisUtils.delByPrefix(TOKEN + uid);
    }

    /**
     * 是否存在
     *
     * @param key
     * @return
     */
    public static boolean exists(String key) {
        return RedisUtils.hasKey(getKey(key));
    }

    /**
     * 续期
     *
     * @param key
     * @return
     */
    public static boolean expire(String key, long time) {
        return RedisUtils.expire(getKey(key), time);
    }

}