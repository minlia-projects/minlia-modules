package com.minlia.module.version.bean.to;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.version.enumeration.PlatformTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

@ApiModel(value = "VersionUTO")
@Data
public class VersionUTO implements ApiRequestBody {

    @ApiModelProperty(value = "ID", example = "1")
    @NotNull(message = "ID不能为空")
    private Long id;

    @ApiModelProperty(value = "版本号", example = "1.0")
    private String number;

    @ApiModelProperty(value = "平台类型", example = "IOS")
    private PlatformTypeEnum type;

    @ApiModelProperty(value = "下载链接", example = "http://xxxxxx")
    @URL
    private String downloadUrl;

    @ApiModelProperty(value = "是否测试", example = "false")
    private Boolean test;

    @ApiModelProperty(value = "备注", example = "XXXXXX")
    private String notes;

    @ApiModelProperty(value = "是否启用", example = "true")
    private Boolean enabled;

    @ApiModelProperty(value = "强制下载", example = "true")
    private Boolean forcedDownload;

}
