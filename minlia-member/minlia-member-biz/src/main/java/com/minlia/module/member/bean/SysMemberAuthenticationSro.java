package com.minlia.module.member.bean;

import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>
 * 个人认证
 * </p>
 *
 * @author garen
 * @since 2020-09-08
 */
@Data
public class SysMemberAuthenticationSro implements ApiRequestBody {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "姓名")
    @NotBlank
    @Size(max = 20)
    private String fullname;

    @ApiModelProperty(value = "身份证号码")
    @NotBlank
    @Size(max = 20)
    private String idNumber;

    @ApiModelProperty(value = "身份证反面照片")
    @NotBlank
    @Size(max = 255)
    private String idFrontPhoto;

    @ApiModelProperty(value = "身份证正面照片")
    @NotBlank
    @Size(max = 255)
    private String idReversePhoto;

    @ApiModelProperty(value = "开户行")
    @NotBlank
    @Size(max = 20)
    private String bankName;

    @ApiModelProperty(value = "银行账号")
    @NotBlank
    @Size(max = 20)
    private String bankAccountName;

    @ApiModelProperty(value = "银行卡号")
    @NotBlank
    @Size(max = 20)
    private String bankCardNumber;

}