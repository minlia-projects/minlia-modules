package com.minlia.module.wallet.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SysWalletAliSro {

    @ApiModelProperty(value = "二级密码")
    @NotBlank
    @Size(min = 6, max = 6)
    private String password;

    //@ApiModelProperty(value = "名称")
    //@NotBlank
    //@Size(max = 20)
    //private String name;

    @ApiModelProperty(value = "号码")
    @NotBlank
    @Size(max = 20)
    private String number;

}