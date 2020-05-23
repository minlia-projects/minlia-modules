package com.minlia.modules.rebecca.bean.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.Password;
import com.minlia.module.common.validation.Username;
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
 * Created by garen on 2017/8/8.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCTO implements ApiRequestBody {

    //    @ApiModelProperty(value = "注册方式")
//    @NotNull(message = "注册方式不能为空")
//
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

    @NotNull
    private String defaultRole;

    private Set<String> roles;

    @JsonIgnore
    private String referral;

    /**
     * 昵称
     */
    @Size(min = 1, max = 11)
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