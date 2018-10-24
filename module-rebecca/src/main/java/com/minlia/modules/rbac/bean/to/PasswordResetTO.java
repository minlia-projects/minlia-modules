package com.minlia.modules.rbac.bean.to;

import com.minlia.module.common.validation.Username;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by garen on 2018/10/24.
 */
@Data
public class PasswordResetTO extends PasswordChangeTO {

    @NotBlank
    @Username
    private String username;

    @NotBlank
    private String code;

}
