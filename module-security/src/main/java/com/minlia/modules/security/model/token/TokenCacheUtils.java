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

    private static String getKey(String guid) {
        return TOKEN + guid;
    }

    private static String getKeyWithSessionId(String guid) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest servletRequest = servletRequestAttributes.getRequest();
        Object sid = servletRequest.getSession().getAttribute(SecurityConstant.SID);
        return String.format(TOKEN_GUID_SESSION, guid, sid);
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
        RedisUtils.delByPrefix(getKey(guid));
//        RedisUtils.del(getKey(guid));
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
