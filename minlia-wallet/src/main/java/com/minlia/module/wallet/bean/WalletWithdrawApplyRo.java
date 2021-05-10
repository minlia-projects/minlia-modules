package com.minlia.module.wallet.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class WalletWithdrawApplyRo {

    @ApiModelProperty(value = "通道")
    @NotNull
    private String channel;

    @NotNull
    @DecimalMin("1")
    private BigDecimal amount;

    @ApiModelProperty(value = "收款人")
    @NotBlank
    private String payee;

    @ApiModelProperty(value = "账号")
    @NotBlank
    private String account;

}