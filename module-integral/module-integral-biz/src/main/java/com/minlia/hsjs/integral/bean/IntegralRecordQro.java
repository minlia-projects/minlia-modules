package com.minlia.hsjs.integral.bean;

import com.minlia.module.data.entity.BaseQueryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * <p>
 * 积分-记录
 * </p>
 *
 * @author garen
 * @since 2021-04-14
 */
@Data
@ApiModel(value = "HsjsIntegralRecordQro")
public class IntegralRecordQro extends BaseQueryEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Long uid;

    @ApiModelProperty(value = "业务ID")
    private Long businessId;

    @ApiModelProperty(value = "业务类型")
    @Size(max = 100)
    private String businessType;

    @ApiModelProperty(value = "分数")
    private Long quantity;

}