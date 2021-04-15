package com.minlia.module.rebecca.user.bean;

import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.Password;
import com.minlia.module.common.validation.Username;
import com.minlia.module.data.entity.BaseQueryEntity;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import com.minlia.module.rebecca.user.enums.SysUserStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author garen
 */
@Data
public class SysUserQro extends BaseQueryEntity {

    @ApiModelProperty(value = "组织ID")
    private Long orgId;

    @ApiModelProperty(value = "用户名")
    @Username
    private String username;

    @ApiModelProperty(value = "手机号码")
    @Cellphone
    private String cellphone;

    @ApiModelProperty(value = "邮箱")
    @Email
    private String email;

    @ApiModelProperty(value = "昵称")
    @Size(min = 1, max = 11)
    private String nickname;

    @ApiModelProperty(value = "密码")
    @Password
    private String password;

    @ApiModelProperty(value = "账号有效时间")
    private LocalDateTime accountEffectiveDate;

    @ApiModelProperty(value = "凭证/密码有效时间")
    private LocalDateTime credentialsEffectiveDate;

    @ApiModelProperty(value = "默认角色")
    private String defaultRole;

    @ApiModelProperty(value = "默认语言")
    private LocaleEnum defaultLocale;

    @ApiModelProperty(value = "状态")
    private SysUserStatusEnum status;

    @ApiModelProperty(value = "最后登录时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "最后登录IP")
    private String lastLoginIp;

    @ApiModelProperty(value = "邀请码")
    private String inviteCode;

    @ApiModelProperty(value = "锁定次数")
    private Integer lockLimit;

    @ApiModelProperty(value = "锁定时间")
    private LocalDateTime lockTime;

    @ApiModelProperty(value = "锁定标识")
    private Boolean lockFlag;

    @ApiModelProperty(value = "禁用标识")
    private Boolean disFlag;

}