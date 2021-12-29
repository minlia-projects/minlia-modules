package com.minlia.module.rebecca.menu.bean;

import com.minlia.module.i18n.enums.LocaleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * @author garen
 */
@Data
@ApiModel(value = "SysMenuSRO")
public class SysMenuSro {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @Min(1)
    private Long id;

    @ApiModelProperty(value = "父ID")
    @NotNull
    @Min(0)
    private Long parentId;

    @ApiModelProperty(value = "名称")
    @NotBlank
    @Size(max = 50)
    private String name;

    @ApiModelProperty(value = "国际化key")
    @Size(max = 100)
    private String i18n;

    @ApiModelProperty(value = "语言环境")
    @NotNull
    private LocaleEnum locale;

//    @ApiModelProperty(value = "类型：根、叶")
//    private NavigationType type;

    @ApiModelProperty(value = "图标")
    @Size(max = 200)
    private String icon;

    @ApiModelProperty(value = "路由")
    @Size(max = 200)
    private String link;

    @ApiModelProperty(value = "排序值")
    @NotNull
    @Min(0)
    @Max(9999)
    private Integer sort;

    @ApiModelProperty(value = "复用标识")
    private Boolean reuseFlag;

    @ApiModelProperty(value = "快捷导航标识")
    private Boolean shortcutFlag;

    @ApiModelProperty(value = "面包线标识")
    private Boolean breadcrumbFlag;

    @ApiModelProperty(value = "隐藏标识")
    private Boolean hideFlag;

    @ApiModelProperty(value = "禁用标识")
    private Boolean disFlag;

}
