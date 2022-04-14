package com.minlia.module.rebecca.user.bean;

import com.minlia.module.data.bean.QueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户-关系
 * </p>
 *
 * @author garen
 * @since 2021-04-14
 */
@Data
@ApiModel(value = "SysUserRelationQro", description = "用户-关系")
public class SysUserRelationQro extends QueryRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "先人")
    private Long ancestor;

    @ApiModelProperty(value = "子孙")
    private Long descendant;

    @ApiModelProperty(value = "等级")
    private Integer level;

    @ApiModelProperty(value = "实名")
    private Boolean realName;

}