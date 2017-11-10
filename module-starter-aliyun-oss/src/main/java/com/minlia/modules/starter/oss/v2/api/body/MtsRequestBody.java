package com.minlia.modules.starter.oss.v2.api.body;

import com.minlia.modules.starter.oss.v2.api.enumeration.MtsTemplateTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("MTS转码请求体")
public class MtsRequestBody {

    @ApiModelProperty(value = "文件地址",example = "video.mp4")
    @NotNull
    private String inputObject;

    @ApiModelProperty(value = "转码后地址",example = "2018-01-01/video.mp4")
    @NotNull
    private String outputObject;

    private MtsTemplateTypeEnum mtsTemplateType;

}
