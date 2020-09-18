package com.minlia.module.rebecca.user.bean;

import com.minlia.module.common.validation.Password;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author garen
 * @date 2018/10/24
 * 根据原密码修改密码
 */
@Data
public class SysPasswordByRawSysPasswordChangeTo extends SysPasswordChangeTo {

    @NotBlank
    private String username;

    /**
     * 原密码
     */
    @NotBlank
    @Password
    private String rawPassword;

}
