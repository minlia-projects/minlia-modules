package com.minlia.module.member.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 企业-银行账户
 * </p>
 *
 * @author garen
 * @since 2021-05-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_bank_account")
@ApiModel(value = "SysBankAccountEntity", description = "银行账户")
public class SysBankAccountEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "企业ID")
    @TableField("enterprise_id")
    private Long enterpriseId;

    @ApiModelProperty(value = "银行名称")
    @TableField("bank_name")
    private String bankName;

    @ApiModelProperty(value = "省")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "市")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "支行名称")
    @TableField("branch_name")
    private String branchName;

    @ApiModelProperty(value = "账户名称")
    @TableField("account_name")
    private String accountName;

    @ApiModelProperty(value = "账户号码")
    @TableField("account_number")
    private String accountNumber;

}