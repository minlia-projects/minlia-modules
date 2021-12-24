package com.minlia.module.member.bean;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.Password;
import com.minlia.module.rebecca.user.enums.SysRegisterTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author garen
 */
@Data
@ApiModel
public class SysMemberRegisterRo implements ApiRequestBody {

    @ApiModelProperty(value = "注册类型")
    @NotNull(message = "注册类型不能为空")
    private SysRegisterTypeEnum type;

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
    @Size(min = 4, max = 8)
    private String captcha;

    @ApiModelProperty("昵称")
    @Size(min = 1, max = 11)
    private String nickname;

}