package com.minlia.modules.security.context;

import com.minlia.modules.security.model.DataPermission;
import com.minlia.modules.security.model.UserContext;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

public class MinliaSecurityContextHolder {

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

    public static DataPermission getDataPermission() {
        UserContext userContext = getUserContext();
        if (Objects.nonNull(userContext)) {
            return DataPermission.builder().orgId(userContext.getOrgId()).dpType(userContext.getDpType()).dpScope(userContext.getDpScope()).build();
        }
        return null;
    }

    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserContext) {
                UserContext userContext = (UserContext) authentication.getPrincipal();
                return userContext.getUsername();
            } else if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return userDetails.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                if ("anonymousUser".equalsIgnoreCase((String) authentication.getPrincipal())) {
                    return null;
                } else {
                    return (String) authentication.getPrincipal();
                }
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
     * 获取用户ID
     *
     * @return
     */
    public static String getName() {
        if (isAnonymousUser()) {
            return "anonymousUser";
        } else {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
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
        Collection<? extends GrantedAuthority> authorities = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return authorities.contains(new SimpleGrantedAuthority(authority));
    }

}