package com.minlia.module.wallet.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class WalletWithdrawApplyRo {

    @ApiModelProperty(value = "通道")
    @NotBlank
    private String channel;

    @ApiModelProperty(value = "收款人")
    @NotNull
    @DecimalMin("1")
    private BigDecimal amount;

    @ApiModelProperty(value = "收款人")
    private String payee;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "收款码")
    @Size(max = 255)
    private String qrcode;

}