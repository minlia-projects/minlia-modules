package com.minlia.module.rebecca.user.bean;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.Password;
import com.minlia.module.common.validation.Username;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import com.minlia.module.rebecca.user.enums.SysUserStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


/**
 * 用户-修改
 *
 * @author garen
 * @date 2017/8/8
 */
@Data
public class SysUserUro implements ApiRequestBody {

    @NotNull
    private Long id;

    private Long orgId;

    @Username
    private String username;

    @Cellphone
    private String cellphone;

    @Email
    private String email;

    @Password
    private String password;

    @ApiModelProperty(value = "头像")
    private String avatar;

    private String defaultRole;

    private LocaleEnum defaultLocale;

    /**
     * 账号有效时间
     */
    private LocalDateTime accountEffectiveDate;

    /**
     * 凭证/密码有效时间
     */
    private LocalDateTime credentialsEffectiveDate;

    /**
     * 状态
     */
    private SysUserStatusEnum status;

}