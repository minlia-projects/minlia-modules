package com.minlia.modules.rebecca.bean.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minlia.module.common.constant.LocalDateConstants;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import com.minlia.modules.rebecca.enumeration.UserStatusEnum;
import com.minlia.modules.rebecca.enumeration.UserUpdateTypeEcnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
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
    private Long guid;

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
    private String password;

    /**
     * 默认角色
     */
    private String defaultRole;

    /**
     * 默认语言
     */
    private LocaleEnum defaultLocale;

    /**
     * 账号有效时间
     */
    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime accountEffectiveDate;

    /**
     * 凭证/密码有效时间
     */
    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime credentialsEffectiveDate;

    /**
     * 状态
     */
    private UserStatusEnum status;

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
    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime lockTime;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 推荐人
     */
    private String referral;

    /**
     * GUID
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime createDate;

    /**
     * GUID
     */
    private String lastModifiedBy;

    /**
     * 最后修改时间
     */
    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime lastModifiedDate;

}