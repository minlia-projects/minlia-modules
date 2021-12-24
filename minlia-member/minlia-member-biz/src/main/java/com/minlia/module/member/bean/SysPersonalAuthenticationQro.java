package com.minlia.module.member.bean;

import com.minlia.module.data.entity.BaseQueryEntity;
import com.minlia.module.member.enums.MemberAuthenticationStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class SysPersonalAuthenticationQro extends BaseQueryEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态")
    private MemberAuthenticationStatusEnum status;

    @ApiModelProperty(value = "姓名")
    @Size(max = 20)
    private String fullname;

    @ApiModelProperty(value = "身份证号码")
    @Size(max = 20)
    private String idNumber;

    @ApiModelProperty(value = "身份证反面照片")
    @Size(max = 255)
    private String idFrontPhoto;

    @ApiModelProperty(value = "身份证正面照片")
    @Size(max = 255)
    private String idReversePhoto;

    @ApiModelProperty(value = "开户行")
    @Size(max = 20)
    private String bankName;

    @ApiModelProperty(value = "银行账号")
    @Size(max = 20)
    private String bankAccountName;

    @ApiModelProperty(value = "银行卡号")
    @Size(max = 20)
    private String bankCardNumber;

}