package com.minlia.module.bible.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by will on 7/5/17.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "数据字典创建请求体")
public class BibleSro {

    @ApiModelProperty(value = "ID", example = "1")
    @Min(0)
    private Long id;

    @ApiModelProperty(value = "编码", example = "gender")
    @NotNull
    @Size(max = 50)
    private String code;

    @ApiModelProperty(value = "值", example = "male")
    @Size(max = 200)
    private String value;

    @ApiModelProperty(value = "描述性说明", example = "用于性别选择")
    @Size(max = 200)
    private String notes;

}
