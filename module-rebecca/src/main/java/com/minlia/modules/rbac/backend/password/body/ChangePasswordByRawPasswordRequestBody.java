package com.minlia.modules.rbac.backend.password.body;

import com.minlia.cloud.constant.ValidationConstants;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by will on 6/19/17.
 * 根据原密码修改密码
 */
@Data
public class ChangePasswordByRawPasswordRequestBody extends ChangePasswordRequestBody {

    /**
     * 密码
     */
    @NotBlank
    @Size(min = ValidationConstants.MIN_SIZE, max = ValidationConstants.MAX_SIZE,message = "Password have to be grater than 8 characters")
    private String rawPassword;

    /**
     * 密码
     */
    @NotBlank
    @Size(min = ValidationConstants.MIN_SIZE, max = ValidationConstants.MAX_SIZE,message = "Password have to be grater than 8 characters")
    private String newPassword;

}
