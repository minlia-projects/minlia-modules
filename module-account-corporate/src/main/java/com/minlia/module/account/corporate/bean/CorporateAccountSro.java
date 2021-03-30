package com.minlia.module.account.corporate.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p>
 * 对公帐号
 * </p>
 *
 * @author garen
 * @since 2021-02-18
 */
@Data
@ApiModel(value = "CorporateAccountSro")
public class CorporateAccountSro {

        private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "ID")
        private Long id;

        @ApiModelProperty(value = "银行名称", required = true)
        @NotBlank
        @Size(max = 200)
        private String bankName;

        @ApiModelProperty(value = "支行名称", required = true)
        @NotBlank
        @Size(max = 200)
        private String branchName;

        @ApiModelProperty(value = "账户名称", required = true)
        @NotBlank
        @Size(max = 100)
        private String accountName;

        @ApiModelProperty(value = "账户号码", required = true)
        @NotBlank
        @Size(max = 30)
        private String accountNumber;

        @ApiModelProperty(value = "币种", required = true)
        @NotBlank
        @Size(max = 10)
        private String currency;

        @ApiModelProperty(value = "备注")
        @Size(max = 255)
        private String remark;

        @ApiModelProperty(value = "是否默认", required = true)
        @NotNull
        private Boolean defFlag;

        @ApiModelProperty(value = "是否禁用", required = true)
        @NotNull
        private Boolean disFlag;

}