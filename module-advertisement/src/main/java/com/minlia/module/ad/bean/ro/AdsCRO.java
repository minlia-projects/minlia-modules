package com.minlia.module.ad.bean.ro;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.ad.enumeration.PlatformEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(value = "AdsCRO")
@Data
public class AdsCRO implements ApiRequestBody {

    @ApiModelProperty(value = "名称", example = "首页")
    @NotBlank(message = "名称不能为空")
    @Size(max = 50)
    private String name;

    @ApiModelProperty(value = "平台", example = "LANDLORD")
    private PlatformEnum platform;

    @ApiModelProperty(value = "投放位置", example = "0")
    @NotBlank(message = "投放位置不能为空")
    private String position;

    @ApiModelProperty(value = "横纵比", example = "180:90")
    private String aspectRatio;

    @ApiModelProperty(value = "备注", example = "XXXXXX")
    @Size(max = 200)
    private String notes;

    @ApiModelProperty(value = "是否启用", example = "true")
    @NotNull(message = "是否启用不能为空")
    private Boolean enabled;

}
