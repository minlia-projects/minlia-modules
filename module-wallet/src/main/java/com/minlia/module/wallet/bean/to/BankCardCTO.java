package com.minlia.module.wallet.bean.to;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.IdCard;
import com.minlia.module.common.validation.NameZh;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@NoArgsConstructor
@ApiModel("银行卡-创建")
public class BankCardCTO implements ApiRequestBody {

//    @ApiModelProperty(value = "银行卡类型")
//    @NotNull(message = "银行卡类型必填")
//    private BankCardType type;

    @ApiModelProperty(value = "联行号")
    @NotBlank(message = "联行号不能为空")
    private String bankCode;

    @ApiModelProperty(value = "银行卡号")
    @NotBlank(message = "银行卡号不能为空")
    private String number;

    @ApiModelProperty(value = "开户人")
    @NotBlank(message = "开户人不能为空")
    @NameZh
    private String holder;

    @ApiModelProperty(value = "身份证号码")
    @NotBlank(message = "身份证号码不能为空")
    @IdCard
    private String idNumber;

    @ApiModelProperty(value = "手机号码")
    @Cellphone
    private String cellphone;

}
