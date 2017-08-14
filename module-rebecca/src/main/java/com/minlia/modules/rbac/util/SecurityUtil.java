package com.minlia.modules.rbac.util;

import com.minlia.modules.rbac.domain.Permission;
import com.minlia.modules.rbac.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by will on 8/15/17.
 */
public class SecurityUtil {

    public final static List<GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    public final static List<String> getPrivileges(final Collection<Role> roles) {
        final List<String> permissions = new ArrayList<String>();
        final List<Permission> collection = new ArrayList<Permission>();
        //添加角色里面的权限点
        for (final Role role : roles) {
            collection.addAll(role.getPermissions());
        }

        //添加权限点
        for (Permission item : collection) {
            permissions.add(item.getCode());
        }
        return permissions;
    }


    public final static List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
