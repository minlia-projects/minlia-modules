package com.minlia.modules.rebecca.context;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.modules.rebecca.service.RoleService;
import com.minlia.modules.rebecca.bean.domain.User;
import com.minlia.modules.rebecca.service.UserQueryService;
import com.minlia.modules.security.context.MinliaSecurityContextHolder;
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
public final class SecurityContextHolder extends MinliaSecurityContextHolder {

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

    public static Boolean hasRole(String roleCode) {
        List<String> roles = ContextHolder.getContext().getBean(RoleService.class).queryCodeByUserId(getCurrentUserId());
        return roles.contains(roleCode);
    }

}
