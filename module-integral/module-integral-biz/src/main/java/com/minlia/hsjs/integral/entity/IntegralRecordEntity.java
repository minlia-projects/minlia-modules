package com.minlia.hsjs.integral.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 积分-记录
 * </p>
 *
 * @author garen
 * @since 2021-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("hsjs_integral_record")
@ApiModel(value = "HsjsIntegralRecordEntity对象", description = "积分-记录")
public class IntegralRecordEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableField("uid")
    private Long uid;

    @ApiModelProperty(value = "业务ID")
    @TableField("business_id")
    private Long businessId;

    @ApiModelProperty(value = "业务类型")
    @TableField("business_type")
    private String businessType;

    @ApiModelProperty(value = "分数")
    @TableField("quantity")
    private Long quantity;

}