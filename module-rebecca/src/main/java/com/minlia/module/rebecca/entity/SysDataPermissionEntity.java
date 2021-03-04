package com.minlia.module.rebecca.entity;

import com.minlia.module.data.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据权限
 * </p>
 *
 * @author garen
 * @since 2021-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_data_permission")
@ApiModel(value="SysDataPermissionEntity对象", description="数据权限")
public class SysDataPermissionEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "方法")
    @TableField("method")
    private String method;

    @ApiModelProperty(value = "策略")
    @TableField("strategy")
    private String strategy;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "是否禁用")
    @TableField("dis_flag")
    private Boolean disFlag;

    @ApiModelProperty(value = "创建人")
    @TableField("create_by")
    private Long createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_date")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "最后修改人")
    @TableField("last_modified_by")
    private Long lastModifiedBy;

    @ApiModelProperty(value = "最后修改时间")
    @TableField("last_modified_date")
    private LocalDateTime lastModifiedDate;


}
