package com.minlia.modules.rbac.context;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.modules.rbac.backend.role.service.RoleService;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.backend.user.service.UserQueryService;
import com.minlia.modules.security.model.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Utility class for Spring Security.
 */
@Slf4j
public final class SecurityContextHolder {

    private SecurityContextHolder() {
    }

    public static UserContext getUserContext() {
        SecurityContext securityContext = org.springframework.security.core.context.SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserContext) {
                UserContext userContext = (UserContext) authentication.getPrincipal();

                log.info("********************************************");
                log.info("********************************************:{}",userContext.toString());
                return userContext;
            }
        }
        return null;
    }

    /**
     * Get the login of the current user.
     */
    public static String getCurrentUsername() {
        SecurityContext securityContext = org.springframework.security.core.context.SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserContext) {
                UserContext userContext = (UserContext) authentication.getPrincipal();
                return userContext.getUsername();
            } else if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                return springSecurityUser.getUsername();
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

    public static User getCurrentUser() {
        String guid = getCurrentGuid();
        if (!StringUtils.isEmpty(guid)) {
            User user = ContextHolder.getContext().getBean(UserQueryService.class).queryByGuidAndNotNull(guid);
            return user;
        } else {
            return null;
        }
    }

    public static Long getCurrentUserId() {
        User user = getCurrentUser();
        return null == user ? null : user.getId();
    }

    public static String getCurrentGuid() {
        return getUserContext().getGuid();
    }

    public static boolean isAuthenticated() {
        SecurityContext securityContext = org.springframework.security.core.context.SecurityContextHolder.getContext();
        Collection<? extends GrantedAuthority> authorities = securityContext.getAuthentication().getAuthorities();
        if (authorities != null) {
            for (GrantedAuthority authority : authorities) {
                if ("anonymousUser".equals(authority.getAuthority())) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Boolean hasRole(String roleCode) {
        List<String> roles = ContextHolder.getContext().getBean(RoleService.class).queryCodeByUserId(getCurrentUserId());
        return roles.contains(roleCode);
    }

    public static boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = org.springframework.security.core.context.SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                return springSecurityUser.getAuthorities().contains(new SimpleGrantedAuthority(authority));
            }
        }
        return false;
    }

}
