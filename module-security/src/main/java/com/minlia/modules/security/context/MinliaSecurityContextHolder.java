package com.minlia.modules.security.context;

import com.minlia.modules.security.model.UserContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MinliaSecurityContextHolder {

    public static UserContext getUserContext() {
        SecurityContext securityContext = org.springframework.security.core.context.SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserContext) {
                UserContext userContext = (UserContext) authentication.getPrincipal();
                return userContext;
            }
        }
        return null;
    }

    public static String getCurrentUsername() {
        SecurityContext securityContext = org.springframework.security.core.context.SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
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

    public static String getCurrentGuid() {
        if (isAnonymousUser()) {
            return null;
        } else {
            return getUserContext().getGuid();
        }
    }

    public static String getUsernameOrCellphoneOrEmail() {
        UserContext userContext = getUserContext();
        if (null != userContext) {
            if (null != userContext.getUsername()) {
                return userContext.getUsername();
            } else if (null != userContext.getCellphone()) {
                return userContext.getCellphone();
            } else if (null != userContext.getEmail()) {
                return userContext.getEmail();
            }
        }
        return "anonymousUser";
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

    public static boolean isAnonymousUser() {
        SecurityContext securityContext = org.springframework.security.core.context.SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return authentication.getPrincipal().equals("anonymousUser");
    }

    public static boolean hasAuthority(String authority) {
        Collection<? extends GrantedAuthority> authorities = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return authorities.contains(new SimpleGrantedAuthority(authority));
    }

}