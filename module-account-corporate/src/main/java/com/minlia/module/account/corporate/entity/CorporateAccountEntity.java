package com.minlia.module.account.corporate.entity;

import com.minlia.module.data.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 对公帐号
 * </p>
 *
 * @author garen
 * @since 2021-02-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_corporate_account")
@ApiModel(value="CorporateAccountEntity对象", description="对公帐号")
public class CorporateAccountEntity extends AbstractEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "银行名称")
    @TableField("bank_name")
    private String bankName;

    @ApiModelProperty(value = "支行名称")
    @TableField("branch_name")
    private String branchName;

    @ApiModelProperty(value = "账户名称")
    @TableField("account_name")
    private String accountName;

    @ApiModelProperty(value = "账户号码")
    @TableField("account_number")
    private String accountNumber;

    @ApiModelProperty(value = "币种")
    @TableField("currency")
    private String currency;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "是否默认")
    @TableField("def_flag")
    private Boolean defFlag;

    @ApiModelProperty(value = "是否禁用")
    @TableField("dis_flag")
    private Boolean disFlag;

    @ApiModelProperty(value = "是否删除")
    @TableField("del_flag")
    @TableLogic
    private Boolean delFlag;

}