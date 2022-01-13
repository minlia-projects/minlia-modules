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

    @ApiModelProperty(value = "二级密码")
    @NotBlank
    @Size(min = 6, max = 6)
    private String password;

    @ApiModelProperty(value = "通道")
    @NotBlank
    private String channel;

    @ApiModelProperty(value = "金额")
    @NotNull
    @DecimalMin("1")
    private BigDecimal amount;

    //@ApiModelProperty(value = "收款人")
    //@Size(max = 32)
    //private String payee;
    //
    //@ApiModelProperty(value = "账号")
    //@Size(max = 32)
    //private String account;
    //
    //@ApiModelProperty(value = "收款码")
    //@Size(max = 255)
    //private String qrcode;

}