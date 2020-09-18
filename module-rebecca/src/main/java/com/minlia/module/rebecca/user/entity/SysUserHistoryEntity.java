package com.minlia.module.rebecca.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.rebecca.user.enums.SysUserUpdateTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户-历史
 * </p>
 *
 * @author garen
 * @since 2020-08-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_history")
@ApiModel(value = "SysUserHistoryEntity对象", description = "用户-历史")
public class SysUserHistoryEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "更新类型")
    @TableField("update_type")
    private SysUserUpdateTypeEnum updateType;

    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
    private Long orgId;

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "手机号码")
    @TableField("cellphone")
    private String cellphone;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty(value = "账号有效时间")
    @TableField("account_effective_date")
    private LocalDateTime accountEffectiveDate;

    @ApiModelProperty(value = "凭证/密码有效时间")
    @TableField("credentials_effective_date")
    private LocalDateTime credentialsEffectiveDate;

    @ApiModelProperty(value = "默认角色")
    @TableField("default_role")
    private String defaultRole;

    @ApiModelProperty(value = "默认语言")
    @TableField("default_locale")
    private String defaultLocale;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private String status;

    @ApiModelProperty(value = "最后登录时间")
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "最后登录IP")
    @TableField("last_login_ip")
    private String lastLoginIp;

    @ApiModelProperty(value = "推荐人")
    @TableField("referral")
    private String referral;

    @ApiModelProperty(value = "锁定次数")
    @TableField("lock_limit")
    private Integer lockLimit;

    @ApiModelProperty(value = "锁定时间")
    @TableField("lock_time")
    private LocalDateTime lockTime;

    @ApiModelProperty(value = "锁定标识")
    @TableField("lock_flag")
    private Boolean lockFlag;

    @ApiModelProperty(value = "禁用标识")
    @TableField("dis_flag")
    private Boolean disFlag;

}