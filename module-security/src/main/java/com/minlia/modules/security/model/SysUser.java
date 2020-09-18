package com.minlia.modules.security.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 当前用户上下文
 */
@Data
public class SysUser extends User {

    private Long uid;

    public SysUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public SysUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    @Override
    public boolean equals(Object rhs) {
        return rhs instanceof SysUser ? this.uid.equals(((SysUser) rhs).uid) : false;
    }

}