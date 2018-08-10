package com.minlia.module.wallet.dto;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.common.validation.IdCard;
import com.minlia.module.wallet.enumeration.BankCardType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@ApiModel("银行卡-创建")
public class BankCardCreateDto implements ApiRequestBody {

    @ApiModelProperty(value = "银行卡类型")
    @NotNull(message = "银行卡类型必填")
    private BankCardType type;

    @ApiModelProperty(value = "开户人")
    @NotBlank(message = "开户人必填")
    private String holder;

    @ApiModelProperty(value = "银行卡号")
    @NotBlank(message = "银行卡号必填")
    private String number;

    @ApiModelProperty(value = "联行号")
    @NotBlank(message = "联行号必填")
    private String bankCode;

}
