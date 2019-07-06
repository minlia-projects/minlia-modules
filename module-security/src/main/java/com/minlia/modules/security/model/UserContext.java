package com.minlia.modules.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minlia.cloud.body.Body;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 当前用户上下文
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UserContext implements Body {

    /**
     * 用户名
     */
    private String username;

    private String cellphone;

    private String email;

    /**
     * 用户唯一编号
     */
    private String guid;

    /**
     * 过期时间
     */
    private LocalDateTime expireDate;

    /**
     * JwtTokenFactory
     * 当前角色
     */
    private String currrole;

    /**
     * 拥有角色
     */
    private List<String> roles;

    /**
     * 拥有菜单
     */
    private List navigations;

    /**
     * 拥有权限点
     */
    private List<String> permissions;

    /**
     * 拥有权限
     */
    @JsonIgnore
    private List<GrantedAuthority> authorities;

}
