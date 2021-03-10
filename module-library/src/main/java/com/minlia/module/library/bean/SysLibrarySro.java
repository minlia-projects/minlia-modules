package com.minlia.module.library.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * <p>
 * 文库
 * </p>
 *
 * @author garen
 * @since 2020-09-11
 */
@Data
@ApiModel(value = "SysLibrarySro", description = "文库")
public class SysLibrarySro implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "编号")
    @Size(max = 32)
    private String number;

    @ApiModelProperty(value = "业务类型")
    @NotBlank
    @Size(max = 20)
    private String type;

    @ApiModelProperty(value = "关键字")
    @NotBlank
    @Size(max = 50)
    private String keyword;

    @ApiModelProperty(value = "名称")
    @NotBlank
    @Size(max = 200)
    private String name;

    @ApiModelProperty(value = "地址")
    @NotBlank
    @Size(max = 255)
    private String url;

    @ApiModelProperty(value = "概要")
    @NotBlank
    @Size(max = 300)
    private String summary;

    @ApiModelProperty(value = "内容")
    @NotBlank
    private String content;

    @ApiModelProperty(value = "备注")
    @NotBlank
    @Size(max = 255)
    private String remark;

    @ApiModelProperty(value = "是否禁用")
    private Boolean disFlag;

}