package com.minlia.modules.rebecca.bean.to;

import com.minlia.module.common.validation.Cellphone;
import com.minlia.modules.rebecca.enumeration.PasswordResetMethodEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by garen on 2018/10/24.
 */
@Data
public class PasswordResetTO extends PasswordChangeTO {

    @NotNull
    private PasswordResetMethodEnum method;

    @Cellphone
    private String cellphone;

    @Email
    private String email;

    @NotBlank
    private String code;

}
