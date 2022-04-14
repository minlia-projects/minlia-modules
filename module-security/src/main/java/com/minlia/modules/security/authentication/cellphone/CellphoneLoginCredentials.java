package com.minlia.modules.security.authentication.cellphone;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.common.validation.Cellphone;
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
 * @author garen
 */
@ApiModel(value = "登陆")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CellphoneLoginCredentials implements ApiRequestBody {

    @ApiModelProperty(value = "区号", example = "86")
    @NotNull
    private Integer areaCode;

    @ApiModelProperty(value = "手机号码", example = "18888888888")
    @NotBlank
    @Cellphone
    private String cellphone;

    @ApiModelProperty(value = "验证码", example = "8888")
    @NotBlank
    @Size(min = 4, max = 8)
    private String vcode;

    @ApiModelProperty(value = "邀请码", example = "Ag146dH")
    @Size(max = 30)
    private String inviteCode;

}