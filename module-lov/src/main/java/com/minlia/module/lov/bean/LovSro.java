package com.minlia.module.lov.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/5/20 4:09 PM
 */
@Data
public class LovSro {

    @ApiModelProperty(value = "自增主键")
    private Long id;

    @ApiModelProperty(value = "所属租户")
    private Integer tenantId;

    @ApiModelProperty(value = "编码")
    @NotBlank
    @Size(max = 50)
    private String code;

    @ApiModelProperty(value = "名称")
    @NotBlank
    @Size(max = 100)
    private String name;

    @ApiModelProperty(value = "排序（升序）")
    @Max(9999)
    private Integer sort;

    @ApiModelProperty(value = "描述信息")
    @Size(max = 200)
    private String description;

    @ApiModelProperty(value = "禁用标记")
    private Boolean disFlag;

}
