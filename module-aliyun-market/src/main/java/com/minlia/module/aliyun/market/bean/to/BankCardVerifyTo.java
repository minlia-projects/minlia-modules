package com.minlia.module.aliyun.market.bean.to;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.IdCard;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankCardVerifyTO implements ApiRequestBody {

    @ApiModelProperty(value = "银行卡号")
    @NotNull(message = "银行卡号不能为空")
    private String number;

    @ApiModelProperty(value = "姓名")
    private String holder;

    @ApiModelProperty(value = "身份证")
    @IdCard
    private String idCard;

    @ApiModelProperty(value = "银行卡号")
    @Cellphone
    private String cellphone;

}
