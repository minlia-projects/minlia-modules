package com.minlia.module.rebecca.user.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minlia.aliyun.green.annotation.Antispam;
import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.Password;
import com.minlia.module.common.validation.Username;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author garen
 * @date 2017/8/8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserCro implements ApiRequestBody {

    private Long orgId;

    @Username
    private String username;

    @Cellphone
    private String cellphone;

    @Email
    private String email;

    @NotBlank
    @Password
    private String password;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @NotNull
    private LocaleEnum defaultLocale;

    @NotNull
    private String defaultRole;

    private Set<String> roles;

    @JsonIgnore
    private String inviteCode;

    /**
     * 昵称
     */
    @Size(min = 1, max = 11)
    @Antispam
    private String nickname;

    /**
     * 账号有效时间
     */
    private LocalDateTime accountEffectiveDate;

    /**
     * 凭证/密码有效时间
     */
    private LocalDateTime credentialsEffectiveDate;

}