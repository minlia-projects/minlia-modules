package com.minlia.modules.rebecca.bean.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.module.data.entity.AbstractEntity;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Created by will on 8/14/17.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({})
@JsonIgnoreProperties(value = {})
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"guid", "username"})
public class User extends AbstractEntity {

    /**
     * Global User Identification
     */
    private String guid;

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
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 默认角色
     */
    private String defaultRole;

    /**
     * 账号有效时间
     */
    private LocalDateTime accountEffectiveDate;

    /**
     * 凭证/密码有效时间
     */
    private LocalDateTime credentialsEffectiveDate;

    /**
     * 是否锁定
     */
    private Boolean locked;

    /**
     * 是否可用
     */
    private Boolean enabled;

    /**
     * 锁定次数
     */
    private Integer lockLimit;

    /**
     * 锁定时间，多少分钟内不能登录
     */
    private LocalDateTime lockTime;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 推荐人
     */
    private String referral;

}