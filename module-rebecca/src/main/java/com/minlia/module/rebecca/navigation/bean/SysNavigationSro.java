package com.minlia.module.rebecca.navigation.bean;

import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author garen
 */
@Data
@ApiModel
public class SysNavigationSro implements ApiRequestBody {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @Min(0)
    private Long id;

    @ApiModelProperty(value = "父ID")
    @NotNull
    @Min(0)
    private Long parentId;

//    @ApiModelProperty(value = "类型：FOLDER-根、LEAF-叶")
//    private String type;

    @ApiModelProperty(value = "名称")
    @NotBlank
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
