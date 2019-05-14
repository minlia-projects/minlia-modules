package com.minlia.modules.security.authentication.credential;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.Password;
import com.minlia.module.common.validation.Username;
import com.minlia.modules.security.enumeration.LoginMethodEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * 只能用其中之一来登录
 * 如: username password
 *
 * 当有多个同时传入时报出错误
 *
 */
@Data
public class LoginCredentials implements WithUsernameCredential, WithEmailCredential, WithCellphoneCredential {
//public class LoginCredentials implements WithUsernameCredential {

    @ApiModelProperty(value = "登陆方式", example = "USERNAME")
    @NotBlank(message = "登陆方式不能为空")
    @JsonIgnore
    private LoginMethodEnum method;

    @ApiModelProperty(value = "用户名", example = "admin")
    @Username
    @JsonIgnore
    private String username;

    @ApiModelProperty(value = "手机号码", example = "18588888888")
    @Cellphone
    @JsonIgnore
    private String cellphone;

    @ApiModelProperty(value = "邮箱", example = "admin@admin.com")
    @Email
    @JsonIgnore
    private String email;

    /*****更改为账号登陆，合并登陆方式*****/

    @ApiModelProperty(value = "用户名/手机号码/邮箱", example = "16888888888")
    @Size(max = 30)
    @JsonProperty("username")
    private String account;

    @ApiModelProperty(value = "密码", example = "admin")
    @NotBlank(message = "密码不能为空")
    @Password
    private String password;

    @ApiModelProperty(value = "验证码", example = "8888")
    @Size(min = 4, max = 8)
    private String captcha;

    @ApiModelProperty(value = "当前角色", example = "User")
    private String currrole;

}
