package com.minlia.modules.rbac.backend.user.body;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.cloud.constant.ValidationConstants;
import com.minlia.module.common.validation.Cellphone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Created by garen on 2017/8/8.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestBody implements ApiRequestBody {

    @NotBlank
    @Size(min = ValidationConstants.MIN_SIZE, max = ValidationConstants.MAX_SIZE, message = "Username have to be grater than 8 characters")
    private String username;

    @Cellphone
    private String cellphone;

    @Email
    private String email;

    @NotBlank
    @Size(min = ValidationConstants.MIN_SIZE, max = ValidationConstants.MAX_SIZE, message = "Password have to be grater than 8 characters")
    private String password;

    @JsonIgnore
    private String referral;

    @JsonIgnore
    private Set<Long> roles;

}