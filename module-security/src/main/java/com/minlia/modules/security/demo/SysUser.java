package com.minlia.modules.security.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * 当前用户上下文
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SysUser implements UserDetails {

    public SysUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = (Set<GrantedAuthority>) authorities;

        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    private Integer id;

    /**
     * 用户唯一编号
     */
    private String username;

    /**
     * 当前角色
     */
    private String password;

    private Set<GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    @Override
    public String toString() {
        return this.username;
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }

}