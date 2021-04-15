package com.minlia.hsjs.integral.bean;

import com.minlia.module.data.entity.BaseQueryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 积分-用户
 * </p>
 *
 * @author garen
 * @since 2021-04-14
 */
@Data
@ApiModel(value = "HsjsIntegralUserQro")
public class HsjsIntegralUserQro extends BaseQueryEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Long uid;

}