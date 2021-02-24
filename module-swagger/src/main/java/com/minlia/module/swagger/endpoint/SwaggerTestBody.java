package com.minlia.module.swagger.endpoint;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Set;

/**
 * @author garen
 */
@ApiModel(value = "SwaggerTestBody")
@Data
public class SwaggerTestBody {

    @ApiModelProperty(value = "ID")
    @NotNull
    @Min(1)
    @Max(10)
    private Long id;

    @ApiModelProperty(value = "名称")
    @NotBlank
    @Size(min = 10, max = 20)
    private String name;

    @ApiModelProperty(value = "IDS")
    @NotEmpty
    private Set<Long> ids;

}