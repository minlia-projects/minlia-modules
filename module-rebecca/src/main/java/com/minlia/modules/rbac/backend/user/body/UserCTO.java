package com.minlia.modules.rbac.backend.user.body;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.Password;
import com.minlia.module.common.validation.Username;
import com.minlia.modules.rbac.enumeration.RegistrationMethodEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by garen on 2017/8/8.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCTO implements ApiRequestBody {

    @ApiModelProperty(value = "注册方式")
    @NotNull(message = "注册方式不能为空")
    private RegistrationMethodEnum method;

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
    private Long defaultRole;

    @NotNull
    private Set<Long> roles;

    @JsonIgnore
    private String referral;

}