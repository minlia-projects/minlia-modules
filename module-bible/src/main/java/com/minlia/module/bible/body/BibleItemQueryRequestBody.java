package com.minlia.module.bible.body;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
public class BibleItemQueryRequestBody {

    @ApiModelProperty(value = "父编码", example = "GENDER")
    @Size(max = 30)
    private String parentCode;

    @ApiModelProperty(value = "编码", example = "male")
    @Size(max = 30)
    private String code;

    @ApiModelProperty(value = "值", example = "1")
    @Size(max = 250)
    private String value;

    @ApiModelProperty(value = "描述性说明", example = "用于性别选择")
    @Size(max = 500)
    private String notes;

}
