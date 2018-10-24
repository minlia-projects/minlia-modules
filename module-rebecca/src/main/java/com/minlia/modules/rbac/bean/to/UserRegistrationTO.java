package com.minlia.modules.rbac.bean.to;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.Password;
import com.minlia.module.common.validation.Username;
import com.minlia.modules.rbac.enumeration.RegistrationMethodEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by will on 6/19/17.
 * 用户注册请求体
 */
@ApiModel(value = "注册")
@Data
public class UserRegistrationTO implements ApiRequestBody {

    @ApiModelProperty(value = "注册类型")
    @NotNull(message = "注册类型不能为空")
    private RegistrationMethodEnum type;

    @ApiModelProperty(value = "用户名", example = "jack")
    @Username
    private String username;

    @ApiModelProperty(value = "手机号码", example = "18888888888")
    @Cellphone
    private String cellphone;

    @ApiModelProperty(value = "邮箱", example = "88888888@qq.com")
    @Email
    private String email;

    @ApiModelProperty(value = "密码", example = "Xx123456")
    @NotBlank(message = "密码不能为空")
    @Password
    private String password;

    @ApiModelProperty(value = "验证码", example = "8888")
    @NotBlank(message = "验证码不能为空")
    @Size(min = 4, max = 6)
    private String code;

    @ApiModelProperty("推荐人")
    @Size(max = 15)
    private String referral;

}