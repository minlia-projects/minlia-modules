package com.minlia.module.rebecca.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 组织-关系
 * </p>
 *
 * @author garen
 * @since 2021-03-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_org_relation")
@ApiModel(value = "SysOrgRelationEntity", description = "组织-关系")
public class SysOrgRelationEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "先人")
    @TableField("ancestor")
    private Long ancestor;

    @ApiModelProperty(value = "子孙")
    @TableField("descendant")
    private Long descendant;

}