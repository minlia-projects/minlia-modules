package com.minlia.modules.rebecca.bean.domain;

import com.minlia.modules.rebecca.enumeration.UserUpdateTypeEcnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserHistory {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 更新类型
     */
    private UserUpdateTypeEcnum updateType;

    /**
     * 唯一ID
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
     * 默认角色
     */
    private String defaultRole;

    /**
     * 密码
     */
    private String password;

    /**
     * 账号有效时间
     */
    private Date accountEffectiveDate;

    /**
     * 凭证/密码有效时间
     */
    private Date credentialsEffectiveDate;

    /**
     * 凭证是否过期
     */
    private Boolean credentialsExpired;

    /**
     * 是否过期
     */
    private Boolean expired;

    /**
     * 是否锁定
     */
    private Boolean locked;

    /**
     * 锁定次数
     */
    private Integer lockLimit;

    /**
     * 锁定时间
     */
    private Date lockTime;

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

    /**
     * GUID
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * GUID
     */
    private String lastModifiedBy;

    /**
     * 最后修改时间
     */
    private Date lastModifiedDate;
}