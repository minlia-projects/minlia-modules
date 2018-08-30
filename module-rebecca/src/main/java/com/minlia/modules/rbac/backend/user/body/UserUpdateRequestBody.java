package com.minlia.modules.rbac.backend.user.body;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.Password;
import com.minlia.module.common.validation.Username;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;


/**
 * 用户-修改
 * Created by garen on 2017/8/8.
 */
@Data
public class UserUpdateRequestBody implements ApiRequestBody {

    @NotBlank
    private String guid;

    @Username
    private String username;

    @Cellphone
    private String cellphone;

    @Email
    private String email;

    @Password
    private String password;

    private String defaultRole;

}