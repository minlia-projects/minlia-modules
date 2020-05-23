package com.minlia.module.bankcard.ro;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.bankcard.enumeration.BankCardType;
import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.IdCard;
import com.minlia.module.common.validation.NameZh;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@ApiModel("银行卡-创建")
public class BankCardCRO implements ApiRequestBody {

    @ApiModelProperty(value = "银行卡类型")
    @NotNull(message = "银行卡类型必填")
    private BankCardType type;

    @ApiModelProperty(value = "联行号")
    @NotBlank(message = "联行号不能为空")
    private String bankCode;

    @ApiModelProperty(value = "银行卡号")
    @NotBlank(message = "银行卡号不能为空")
    private String number;

    @ApiModelProperty(value = "开户人", example = "于含烟")
    @NotBlank(message = "开户人不能为空")
    @NameZh
    private String holder;

    @ApiModelProperty(value = "身份证号码", example = "360602198305142880")
    @NotBlank(message = "身份证号码不能为空")
    @IdCard
    private String idNumber;

    @ApiModelProperty(value = "手机号码", example = "18066297716")
    @Cellphone
    private String cellphone;

    @ApiModelProperty(value = "用户编号", example = "100000")
    @Size(max = 15)
    private String guid;

}
