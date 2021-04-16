package com.minlia.hsjs.integral.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 积分-用户
 * </p>
 *
 * @author garen
 * @since 2021-04-15
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("hsjs_integral_user")
@ApiModel(value = "HsjsIntegralUserEntity对象", description = "积分-用户")
public class IntegralUserEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableField("uid")
    private Long uid;

    @ApiModelProperty(value = "可用分数")
    @TableField("available")
    private Long available;

    @ApiModelProperty(value = "冻结分数")
    @TableField("freeze")
    private Long freeze;

    @ApiModelProperty(value = "总分数")
    @TableField("total")
    private Long total;

    @ApiModelProperty(value = "累计分数")
    @TableField("grand_total")
    private Long grandTotal;

}