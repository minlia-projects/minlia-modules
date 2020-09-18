package com.minlia.module.rebecca.user.bean;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.common.validation.Password;

import javax.validation.constraints.NotBlank;

/**
 * @author garen
 * @date 2018/10/24
 */
public class SysPasswordChangeTo implements ApiRequestBody {

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
