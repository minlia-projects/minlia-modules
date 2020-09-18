package com.minlia.module.bible.bean;

import com.minlia.module.data.bean.QueryRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class BibleItemQro extends QueryRequest {

    @ApiModelProperty(value = "父编码", example = "GENDER")
    @Size(max = 30)
    private String parentCode;

    @ApiModelProperty(value = "编码", example = "male")
    @Size(max = 30)
    private String code;

    @ApiModelProperty(value = "值", example = "1")
    @Size(max = 250)
    private String value;

    private String attribute1;

}
