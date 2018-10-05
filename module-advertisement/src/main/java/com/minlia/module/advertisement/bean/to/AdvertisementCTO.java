package com.minlia.module.advertisement.bean.to;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.advertisement.enumeration.AdTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(value = "AdvertisementCTO")
@Data
public class AdvertisementCTO implements ApiRequestBody {

    @ApiModelProperty(value = "父ID", example = "1")
    @NotNull
    private Long parentId;

    @ApiModelProperty(value = "名称", example = "首页")
    @NotBlank(message = "名称不能为空")
    @Size(max = 50)
    private String name;

//    @ApiModelProperty(value = "封面", example = "封面")
//    @NotNull(message = "封面不能为空")
//    @Size(max = 300)
//    @URL
//    private String cover;

    @ApiModelProperty(value = "封面eTag", example = "封面eTag")
    @NotNull(message = "封面不能为空")
    @Size(max = 300)
    private String coverETag;

    @ApiModelProperty(value = "链接类型", example = "URL")
    @NotNull(message = "封面不能为空")
    private AdTypeEnum type;

    @ApiModelProperty(value = "链接地址", example = "https://xxx.com")
    @NotNull
    @Size(max = 300)
    private String path;

    @ApiModelProperty(value = "备注", example = "XXXXXX")
    @Size(max = 200)
    private String notes;

    @ApiModelProperty(value = "是否启用", example = "true")
    @NotNull(message = "是否启用不能为空")
    private Boolean enabled;

}
