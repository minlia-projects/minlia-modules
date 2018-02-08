package com.minlia.module.bible.body;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BibleUpdateRequestBody {

    @ApiModelProperty(value = "ID", example = "1")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "值", example = "male")
    @Size(max = 255)
    private String value;

    @ApiModelProperty(value = "描述性说明", example = "用于性别选择")
    @Size(max = 255)
    private String notes;

}
