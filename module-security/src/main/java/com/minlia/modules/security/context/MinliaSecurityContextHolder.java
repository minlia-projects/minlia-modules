package com.minlia.modules.security.context;

import com.minlia.modules.security.model.DataPermission;
import com.minlia.modules.security.model.UserContext;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Objects;

/**
 * @author garen
 */
public class MinliaSecurityContextHolder {

    /**
     * 获取上下文
     *
     * @return
     */
    public static UserContext getUserContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserContext) {
                UserContext userContext = (UserContext) authentication.getPrincipal();
                return userContext;
            }
        }
        return null;
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public static Long getUid() {
        if (isAnonymousUser()) {
            return NumberUtils.LONG_MINUS_ONE;
        } else {
            return getUserContext().getUid();
        }
    }

    /**
     * 获取用户名
     *
     * @return
     */
    public static String getUsername() {
        if (isAnonymousUser()) {
            return "anonymousUser";
        } else {
            return getUserContext().getUsername();
        }
    }

    /**
     * 获取用户缓存KEY
     *
     * @return
     */
    public static String getKey() {
        if (isAnonymousUser()) {
            return "anonymousUser";
        } else {
            return getUserContext().getKey();
        }
    }

    /**
     * 获取数据权限
     *
     * @return
     */
    public static DataPermission getDataPermission() {
        UserContext userContext = getUserContext();
        if (Objects.nonNull(userContext)) {
            return DataPermission.builder().orgId(userContext.getOrgId()).dpType(userContext.getDpType()).dpScope(userContext.getDpScope()).build();
        }
        return null;
    }

    /**
     * 是否匿名用户
     *
     * @return
     */
    public static boolean isAnonymousUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication) {
            return true;
        }
        return authentication.getPrincipal().equals("anonymousUser");
    }

    /**
     * 是否有权限
     *
     * @param authority
     * @return
     */
    public static boolean hasAuthority(String authority) {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return authorities.contains(new SimpleGrantedAuthority(authority));
    }

}