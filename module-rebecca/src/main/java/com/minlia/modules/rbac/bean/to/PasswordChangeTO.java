package com.minlia.modules.rbac.bean.to;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.common.validation.Password;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by garen on 2018/10/24.
 */
public class PasswordChangeTO implements ApiRequestBody {

    /**
     * 新密码
     */
    @NotBlank
    @Password
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
