package com.minlia.modules.rbac.context;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.modules.rbac.backend.role.entity.Role;
import com.minlia.modules.rbac.backend.role.service.RoleService;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.backend.user.service.UserQueryService;
import com.minlia.modules.security.model.UserContext;
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
public final class SecurityContextHolder {

    private SecurityContextHolder() {
    }

    public static UserContext getUserContext() {
        SecurityContext securityContext = org.springframework.security.core.context.SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserContext) {
                UserContext userContext = (UserContext) authentication.getPrincipal();

                //添加navigations 到当前用户上下文
//                List<Navigation> navigationList = Lists.newArrayList();
//                Set<LoginLog> roles = getCurrentUser().getRoles();
//                if (null != roles && roles.size() > 0) {
//                    for (LoginLog role : roles) {
//                        if(null!=role.getNavigations()) {
//                            navigationList.addAll(role.getNavigations());
//                        }
////                        Set<Navigation> navigations = role.getNavigations();
////                        if (null != navigations && navigations.size() > 0) {
////                            for (Navigation navigation : navigations) {
////                                Navigation item = new Navigation();
////                                item.setParent(null);
////                                item.setName(navigation.getName());
////                                item.setIcon(navigation.getIcon());
////                                item.setState(navigation.getState());
////                                item.setId(navigation.getId());
////                                navigationList.add(item);
////                            }
////                        }
//                    }
//                }
//                if (null != navigationList && navigationList.size() > 0) {
//                    userContext.setNavigations(navigationList);
//                }
                return userContext;
            }
        }
        return null;
    }

    /**
     * Get the login of the current user.
     */
    public static String getCurrentUserLogin() {
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
                 if ("anonymousUser".equalsIgnoreCase((String)authentication.getPrincipal())) {
                  return null;
                 }else{
                     return (String) authentication.getPrincipal();
                 }
            }
        }
        return null;
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
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

    /**
     * Return the current user, or throws an exception, if the user is not
     * authenticated yet.
     *
     * @return the current user
     */
    public static User getCurrentUser() {
        String username = getCurrentUserLogin();
        if (!StringUtils.isEmpty(username)) {
//            User user = ContextHolder.getContext().getBean(UserQueryService.class).queryByUsernameOrCellphoneOrEmail(username,username,username);
            User user = ContextHolder.getContext().getBean(UserQueryService.class).queryByGuid(username);
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
        return getCurrentUserLogin();
    }

    public static Boolean hasRole(String roleCode){
        List<String> roles = ContextHolder.getContext().getBean(RoleService.class).queryCodeByUserId(getCurrentUserId());
        return roles.contains(roleCode);
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * <p>The name of this method comes from the isUserInRole() method in the Servlet API</p>
     */
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
