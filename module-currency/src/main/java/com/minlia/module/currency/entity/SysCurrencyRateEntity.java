package com.minlia.module.currency.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 货币汇率
 * </p>
 *
 * @author garen
 * @since 2021-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_currency_rate")
@ApiModel(value = "SysCurrencyRateEntity对象", description = "货币汇率")
public class SysCurrencyRateEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "汇率对")
    @TableField("symbol")
    private String symbol;

    @ApiModelProperty(value = "基础币种")
    @TableField("cur_base")
    private String curBase;

    @ApiModelProperty(value = "交易币种")
    @TableField("cur_trans")
    private String curTrans;

    @ApiModelProperty(value = "汇率")
    @TableField("rate")
    private BigDecimal rate;

    @ApiModelProperty(value = "时间")
    @TableField("time")
    private LocalDate time;

    @ApiModelProperty(value = "自动更新")
    @TableField("auto_flag")
    private Boolean autoFlag;

}