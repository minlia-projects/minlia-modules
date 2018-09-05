package com.minlia.module.data.context;

import com.minlia.modules.security.model.UserContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author garen
 * @date 2018/3/2
 */
public class UserPrincipalHolder {

    public static String getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserContext) {
                UserContext userContext = (UserContext) authentication.getPrincipal();
                return userContext.getGuid();
            } else if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                return springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                if ("anonymousUser".equalsIgnoreCase((String)authentication.getPrincipal())) {
                    return null;
                }else{
                    return (String) authentication.getPrincipal();
                }
            }
        }
        return null;
    }

}
