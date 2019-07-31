package com.minlia.modules.security.model.token;

import com.minlia.module.common.constant.SymbolConstants;
import com.minlia.module.redis.util.RedisUtils;
import com.minlia.modules.security.autoconfiguration.WebSecurityConfig;
import com.minlia.modules.security.constant.SecurityConstant;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

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

//        return TOKEN + guid;
    }

    /**
     * 缓存token
     *
     * @param guid
     * @param token
     * @param expirationTime
     */
    public static void cache(String guid, String token, int expirationTime) {
        RedisUtils.set(getKeyWithSessionId(guid), token, expirationTime);
    }

    /**
     * 获取token
     *
     * @param guid
     */
    public static Object get(String guid) {
        return RedisUtils.get(getKeyWithSessionId(guid));
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
        return RedisUtils.getRedisTemplate().hasKey(getKeyWithSessionId(guid));
    }

    /**
     * 续期
     *
     * @param guid
     * @return
     */
    public static boolean expire(String guid, long time) {
        return RedisUtils.expire(getKeyWithSessionId(guid), time);
    }

}
