package com.minlia.modules.security.model.token;

import com.minlia.cloud.constant.SymbolConstants;
import com.minlia.module.redis.config.RedisFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author garen
 */
public class TokenCacheUtils {

    public static final String TOKEN = "token:";
    public static final Integer DB_INDEX = 1;
    private static RedisTemplate<String, Object> redisTemplate = RedisFactory.get(DB_INDEX);

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
    public static String get(String key) {
        return (String) redisTemplate.opsForValue().get(getKey(key));
    }

    /**
     * 缓存token
     *
     * @param key
     * @param token
     * @param expireTime
     */
    public static void cache(String key, String token, long expireTime) {
        redisTemplate.opsForValue().set(getKey(key), token, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 杀掉token
     *
     * @param key
     */
    public static void kill(String key) {
        redisTemplate.delete(getKey(key));
    }

    /**
     * 杀掉token
     *
     * @param uid
     */
    public static void kill(Long uid) {
        Set<String> keys = redisTemplate.keys(TOKEN + uid + SymbolConstants.STAR);
        redisTemplate.delete(keys);
    }

    /**
     * 是否存在
     *
     * @param key
     * @return
     */
    public static boolean exists(String key) {
        return redisTemplate.hasKey(getKey(key));
    }

    /**
     * 续期
     *
     * @param key
     * @return
     */
    public static boolean expire(String key, long time) {
        return redisTemplate.expire(getKey(key), time, TimeUnit.SECONDS);
    }

    /**
     * 获取KEY
     *
     * @param key
     * @return
     */
    private static String getKey(String key) {
        return TOKEN + key;
    }

}