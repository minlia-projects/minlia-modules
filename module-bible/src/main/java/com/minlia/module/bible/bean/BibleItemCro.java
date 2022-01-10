package com.minlia.module.bible.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author garen
 */
@Data
public class BibleItemCro implements ApiRequestBody {

    @ApiModelProperty(value = "父编码", example = "GENDER")
    @NotNull
    @Size(max = 50)
    private String parentCode;

    @ApiModelProperty(value = "编码", example = "male")
    @NotNull
    @Size(max = 30)
    private String code;

    @ApiModelProperty(value = "值", example = "1")
    @Size(max = 250)
    private String value;

    @ApiModelProperty(value = "是否公开")
    private Boolean openFlag;

    @ApiModelProperty(value = "描述性说明", example = "用于性别选择")
    @Size(max = 500)
    private String notes;

    @ApiModelProperty(value = "排序", example = "1")
    @Max(250)
    private Integer sorts;

    @ApiModelProperty(value = "扩展1", example = "1")
    @Size(max = 250)
    private String attribute1;

    @ApiModelProperty(value = "扩展2", example = "1")
    @Size(max = 250)
    private String attribute2;

    @ApiModelProperty(value = "扩展3", example = "1")
    @Size(max = 250)
    private String attribute3;

}
