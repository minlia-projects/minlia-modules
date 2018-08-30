package com.minlia.modules.rbac.backend.user.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.module.data.entity.AbstractEntity;
import lombok.*;

import java.util.Date;

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
@EqualsAndHashCode(of = {"guid","username"})
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
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 默认角色
     */
    private String defaultRole;

    /**
     * 凭证是否有效
     */
    private Boolean credentialsExpired;

    /**
     * 有效期
     */
    private Date expireDate;

    /**
     * 是否锁定
     */
    private Boolean locked;

    /**
     * 锁定次数
     */
    private Integer lockLimit;

    /**
     * 锁定时间，多少分钟内不能登录
     */
    private Date lockTime;

//    @JsonProperty("lockTime")
//    public Long getLockTimeEpochDay(){
//        return null != lockTime ? lockTime.toEpochSecond(ZoneOffset.of("+8")) : null;
//    }

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 推荐人
     */
    private String referral;

    /**
     * 是否启用
     */
    private Boolean enabled;

}
