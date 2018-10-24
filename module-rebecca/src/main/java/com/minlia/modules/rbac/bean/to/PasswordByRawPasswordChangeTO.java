package com.minlia.modules.rbac.bean.to;

import com.minlia.module.common.validation.Password;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by garen on 2018/10/24.
 * 根据原密码修改密码
 */
@Data
public class PasswordByRawPasswordChangeTO extends PasswordChangeTO {

    /**
     * 原密码
     */
    @NotBlank
    @Password
    private String rawPassword;

}
