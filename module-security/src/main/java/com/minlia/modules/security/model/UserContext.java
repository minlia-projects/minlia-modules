package com.minlia.modules.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minlia.cloud.body.Body;
import com.minlia.cloud.constant.SymbolConstants;
import com.minlia.module.common.annotation.SensitiveInfo;
import com.minlia.module.common.enums.SensitiveTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 当前用户上下文
 *
 * @author will
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserContext implements Body {

    /**
     * 用户唯一编号
     */
    private Long uid;

    /**
     * 组织ID
     */
    @JsonIgnore
    private Long orgId;
    @JsonIgnore
    private Integer dpType;
    @JsonIgnore
    private String dpScope;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号码
     */
    @SensitiveInfo(value = SensitiveTypeEnum.MOBILE_PHONE)
    private String cellphone;

    /**
     * 邮箱
     */
    @SensitiveInfo(value = SensitiveTypeEnum.EMAIL)
    private String email;

    /**
     * 过期时间
     */
    private LocalDateTime expireDate;

    /**
     * JwtTokenFactory
     * 当前角色
     */
    private String currrole;

    @JsonIgnore
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

    public String getKey() {
        return this.uid + SymbolConstants.COLON + this.currrole.hashCode();
    }

}