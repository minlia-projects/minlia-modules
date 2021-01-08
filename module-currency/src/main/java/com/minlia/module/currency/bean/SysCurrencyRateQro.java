package com.minlia.module.currency.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.minlia.module.data.bean.QueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 货币
 * </p>
 *
 * @author garen
 * @since 2021-01-08
 */
@Data
@ApiModel(value = "SysCurrencyRateQro", description = "货币")
public class SysCurrencyRateQro extends QueryRequest {

    @ApiModelProperty(value = "汇率对")
    @TableField("symbol")
    private String symbol;

    @ApiModelProperty(value = "基础货币")
    @TableField("cur_base")
    private String curBase;

    @ApiModelProperty(value = "转换货币")
    @TableField("cur_trans")
    private String curTrans;

    @ApiModelProperty(value = "汇率")
    @TableField("rate")
    private BigDecimal rate;

    @ApiModelProperty(value = "时间")
    @TableField("time")
    private LocalDateTime time;

    @ApiModelProperty(value = "自动更新")
    @TableField("auto_flag")
    private Boolean autoFlag;

}