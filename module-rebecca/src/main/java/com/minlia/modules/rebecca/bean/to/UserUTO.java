package com.minlia.modules.rebecca.bean.to;

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
public class UserUTO implements ApiRequestBody {

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

    private Long defaultRole;

}