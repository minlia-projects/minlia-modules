package com.minlia.modules.rbac.backend.user.body;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.Password;
import com.minlia.module.common.validation.Username;
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
public class UserCreateRequestBody implements ApiRequestBody {

    @Username
    private String username;

    @Cellphone
    private String cellphone;

    @Email
    private String email;

    @NotBlank
    @Password
    private String password;

    @NotBlank
    private String defaultRole;

    @NotNull
    private Set<Long> roles;

    @JsonIgnore
    private String referral;

}