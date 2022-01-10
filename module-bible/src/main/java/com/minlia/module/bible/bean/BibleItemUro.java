package com.minlia.module.bible.bean;

import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class BibleItemUro implements ApiRequestBody {

    @NotNull
    private Long id;

    @ApiModelProperty(value = "值", example = "1")
    @Size(max = 255)
    private String value;

    @ApiModelProperty(value = "是否公开")
    private Boolean openFlag;

    @ApiModelProperty(value = "描述性说明", example = "用于性别选择")
    @Size(max = 255)
    private String notes;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sorts;

    @ApiModelProperty(value = "扩展1", example = "1")
    @Size(max = 255)
    private String attribute1;

    @ApiModelProperty(value = "扩展2", example = "1")
    @Size(max = 255)
    private String attribute2;

    @ApiModelProperty(value = "扩展3", example = "1")
    @Size(max = 255)
    private String attribute3;

}
