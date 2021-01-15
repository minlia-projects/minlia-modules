package com.minlia.modules.security.authentication.credential;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.Password;
import com.minlia.module.common.validation.Username;
import com.minlia.modules.security.enumeration.LoginTypeEnum;
import lombok.Data;
import org.springframework.security.core.AuthenticatedPrincipal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 只能用其中之一来登录
 * 如: username password
 * <p>
 * 当有多个同时传入时报出错误
 */
@Data
public class LoginCredentials implements AuthenticatedPrincipal, WithUsernameCredential, WithEmailCredential, WithCellphoneCredential {

    /**
     * 登陆方式
     */
    @NotBlank(message = "登陆方式不能为空")
    @JsonIgnore
    private LoginTypeEnum type;

    /**
     * 用户名
     */
    @Username
    @JsonIgnore
    private String username;

    private Integer areaCode;

    /**
     * 手机号码
     */
    @Cellphone
    @JsonIgnore
    private String cellphone;

    /**
     * 邮箱
     */
    @Email
    @JsonIgnore
    private String email;

    /**
     * 用户名/手机号码/邮箱
     */
    @Size(max = 30)
    @JsonProperty("username")
    private String name;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Password
    private String password;

    /**
     * 验证码
     */
    @Size(min = 4, max = 8)
    private String vcode;

    /**
     * 当前角色
     */
    private String currrole;

}
