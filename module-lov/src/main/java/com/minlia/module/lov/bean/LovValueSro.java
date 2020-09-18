package com.minlia.module.lov.bean;

import com.minlia.module.i18n.enumeration.LocaleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/5/20 4:09 PM
 */
@Data
public class LovValueSro {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增主键")
    @Min(0)
    private Long id;

    @ApiModelProperty(value = "租户ID")
    private Integer tenantId;

    @ApiModelProperty(value = "值集ID")
    @NotNull
    @Min(0)
    private Long lovId;

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

    @ApiModelProperty(value = "语言环境")
    @NotNull
    private LocaleEnum locale;

    @ApiModelProperty(value = "禁用标记")
    private Boolean disFlag;

}
