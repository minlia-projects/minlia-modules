package com.minlia.modules.security.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minlia.cloud.body.Body;
import com.minlia.module.common.constant.LocalDateConstants;
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

    /**
     * 手机号码
     */
    private String cellphone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户唯一编号
     */
    private String guid;

    private String parentGuid;

    /**
     * JwtTokenFactory
     * 当前角色
     */
    private String currrole;

    private String currdomain;

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

    /**
     * 过期时间
     */
    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)
    private LocalDateTime expireDate;

}
