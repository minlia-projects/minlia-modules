package com.minlia.modules.rebecca.bean.to;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by garen on 2018/10/24.
 */
@Data
public class PasswordByCaptchaChangeTO extends PasswordChangeTO {

    /**
     * 验证码
     */
    @NotBlank
    private String code;

}
