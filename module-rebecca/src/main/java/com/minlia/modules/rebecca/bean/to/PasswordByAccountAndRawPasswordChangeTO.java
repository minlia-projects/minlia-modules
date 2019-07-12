package com.minlia.modules.rebecca.bean.to;

import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.Password;
import com.minlia.module.common.validation.Username;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by garen on 2018/10/24.
 * 根据原密码修改密码
 */
@Data
public class PasswordByAccountAndRawPasswordChangeTO extends PasswordChangeTO {

//    @Username
//    private String username;
//
//    @Cellphone
//    private String cellphone;
//
//    @Email
//    private String email;

    @NotBlank
    private String username;

    /**
     * 原密码
     */
    @NotBlank
    @Password
    private String rawPassword;

}
