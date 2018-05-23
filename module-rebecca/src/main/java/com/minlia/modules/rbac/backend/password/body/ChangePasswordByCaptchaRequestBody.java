package com.minlia.modules.rbac.backend.password.body;

import com.minlia.cloud.constant.ValidationConstants;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by will on 6/19/17.
 */
@Data
public class ChangePasswordByCaptchaRequestBody extends ChangePasswordRequestBody {

    /**
     * 验证码
     */
    @NotNull
    private String code;

    /**
     * 密码
     */
    @NotNull
    @Size(min = ValidationConstants.MIN_SIZE, max = ValidationConstants.MAX_SIZE, message = "Password have to be grater than 8 characters")
    private String newPassword;

}
