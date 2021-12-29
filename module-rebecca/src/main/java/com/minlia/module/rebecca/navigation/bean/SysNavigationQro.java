package com.minlia.module.rebecca.navigation.bean;

import com.minlia.module.common.enums.TreeNodeTypeEnum;
import com.minlia.module.data.entity.BaseQueryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * @author garen
 */
@Data
@ApiModel
public class SysNavigationQro extends BaseQueryEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父ID")
    @Min(0)
    private Long parentId;

    @ApiModelProperty(value = "类型：FOLDER-根、LEAF-叶")
    private TreeNodeTypeEnum type;

    @ApiModelProperty(value = "名称")
    @Size(max = 50)
    private String name;

    @ApiModelProperty(value = "国际化KEY")
    @Size(max = 100)
    private String i18n;

    @ApiModelProperty(value = "图标")
    @Size(max = 200)
    private String icon;

    @ApiModelProperty(value = "路由")
    @Size(max = 200)
    private String state;

    @ApiModelProperty(value = "排序")
    @Min(0)
    private Integer sort;

    @ApiModelProperty(value = "隐藏标识")
    private Boolean hideFlag;

    @ApiModelProperty(value = "禁用标识")
    private Boolean disFlag;

    @ApiModelProperty(value = "删除标识")
    private Boolean delFlag;

}
