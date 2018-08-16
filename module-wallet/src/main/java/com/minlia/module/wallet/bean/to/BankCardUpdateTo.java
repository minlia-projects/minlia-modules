package com.minlia.module.wallet.bean.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@ApiModel("银行卡-修改")
public class BankCardUpdateTo extends BankCardCreateTo {

    @ApiModelProperty(value = "ID")
    @NotNull(message = "ID必填")
    private Long id;

}
