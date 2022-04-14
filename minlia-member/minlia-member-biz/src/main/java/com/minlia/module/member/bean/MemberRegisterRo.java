package com.minlia.module.rebecca.user.bean;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.Password;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author will
 * @date 6/19/17
 * 用户注册请求体
 */
@ApiModel(value = "注册")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterRo implements ApiRequestBody {

    @ApiModelProperty(value = "区号", example = "86")
    @NotNull
    private Integer areaCode;

    @ApiModelProperty(value = "手机号码", example = "18888888888")
    @NotBlank
    @Cellphone
    private String cellphone;

    @ApiModelProperty(value = "密码", example = "Xx123456")
    @NotBlank(message = "密码不能为空")
    @Password
    private String password;

    @ApiModelProperty(value = "验证码", example = "8888")
    @NotBlank
    @Size(min = 4, max = 8)
    private String vcode;

    @ApiModelProperty("邀请码")
    @Size(max = 30)
    private String inviteCode;

}