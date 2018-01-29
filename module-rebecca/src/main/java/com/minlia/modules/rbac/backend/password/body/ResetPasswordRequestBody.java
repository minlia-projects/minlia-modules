package com.minlia.modules.rbac.backend.password.body;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.cloud.constant.ValidationConstants;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by will on 6/19/17.
 */
@Data
public class ResetPasswordRequestBody implements ApiRequestBody{

    @NotBlank
    @Size(min = ValidationConstants.MIN_SIZE, max = ValidationConstants.MAX_SIZE,message = "Username have to be grater than 8 characters")
    private String username;

    @NotBlank
    @Size(min = ValidationConstants.MIN_SIZE, max = ValidationConstants.MAX_SIZE,message = "Password have to be grater than 8 characters")
    private String newPassword;

    @NotBlank
    private String code;

}
