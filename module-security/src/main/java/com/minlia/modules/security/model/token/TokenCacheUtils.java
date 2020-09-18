package com.minlia.modules.security.model.token;

import com.minlia.module.redis.util.RedisUtils;
import com.minlia.modules.security.constant.SecurityConstant;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/7/11 11:09 AM
 */
public class TokenCacheUtils {

    public static final String TOKEN = "token:";
    public static final String TOKEN_GUID_SESSION = "token:%s_%s";

    public static final String TOKEN_R = "token.r:";

    private static String getKey(Long uid) {
        return TOKEN + uid;
    }

    private static String getKeyWithSessionId(Long uid) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest servletRequest = servletRequestAttributes.getRequest();
        Object sid = servletRequest.getSession().getAttribute(SecurityConstant.SID);
        return String.format(TOKEN_GUID_SESSION, uid, sid);
    }

    /**
     * 缓存token
     *
     * @param uid
     * @param token
     * @param expirationTime
     */
    public static void cache(Long uid, String token, int expirationTime) {
        RedisUtils.set(getKey(uid), token, expirationTime);
    }

    /**
     * 获取token
     *
     * @param uid
     */
    public static Object get(Long uid) {
        return RedisUtils.get(getKey(uid));
    }

    /**
     * 杀掉token
     *
     * @param uid
     */
    public static void kill(Long uid) {
        RedisUtils.delByPrefix(getKey(uid));
//        RedisUtils.del(getKey(uid));
    }

    /**
     * 是否存在
     *
     * @param uid
     * @return
     */
    public static boolean exists(Long uid) {
        return RedisUtils.getRedisTemplate().hasKey(getKey(uid));
    }

    /**
     * 续期
     *
     * @param uid
     * @return
     */
    public static boolean expire(Long uid, long time) {
        return RedisUtils.expire(getKey(uid), time);
    }

}
