package com.minlia.modules.security.model;

import com.minlia.cloud.body.Body;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.List;

/**
 * 当前用户上下文
 */
@Data
public class UserContext implements Body {

    /**
     * 用户名
     */
    private final String username;

    /**
     * 过期时间
     */
    private  Date expireDate;

    /**
     * 当前角色
     */
    private String currrole;

    /**
     * 权限点
     * [{'authority':'user.change.password'}]
     */
    private final List<GrantedAuthority> authorities;

    /**
     * 我拥有的菜单项
     */
//    private List<Navigation> navigations= Lists.newArrayList();

    private UserContext(String username, List<GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
    }

    private UserContext(String username, List<GrantedAuthority> authorities, Date expireDate) {
        this.username = username;
        this.authorities = authorities;
        this.expireDate = expireDate;
    }

    public static UserContext create(String username, List<GrantedAuthority> authorities) {
        return new UserContext(username, authorities, null);
    }

    public static UserContext create(String username, List<GrantedAuthority> authorities, Date expirDate) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("Username is blank: " + username);
        }
        return new UserContext(username, authorities, expirDate);
    }

}
