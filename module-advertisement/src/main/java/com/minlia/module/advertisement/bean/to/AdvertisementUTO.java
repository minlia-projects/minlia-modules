package com.minlia.module.advertisement.bean.to;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.advertisement.enumeration.AdTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(value = "AdvertisementUTO")
@Data
public class AdvertisementUTO implements ApiRequestBody {

    @ApiModelProperty(value = "ID", example = "1")
    @NotNull(message = "ID不能为空")
    private Long id;

    @ApiModelProperty(value = "名称", example = "首页")
    @Size(max = 50)
    private String name;

//    @ApiModelProperty(value = "封面", example = "封面")
//    private String cover;

    @ApiModelProperty(value = "封面eTag", example = "封面eTag")
    @Size(max = 300)
    private String coverETag;

    @ApiModelProperty(value = "链接类型", example = "url")
    private AdTypeEnum type;

    @ApiModelProperty(value = "链接地址", example = "url")
    private String path;

    @ApiModelProperty(value = "备注", example = "XXXXXX")
    private String notes;

    @ApiModelProperty(value = "是否启用", example = "true")
    private Boolean enabled;

}
