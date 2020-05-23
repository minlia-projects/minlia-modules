package com.minlia.module.version.bean.ro;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.version.enumeration.PlatformTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(value = "VersionCRO")
@Data
public class VersionCRO implements ApiRequestBody {

    @ApiModelProperty(value = "版本号", example = "1.0")
    @NotBlank(message = "版本号不能为空")
    private String number;

    @ApiModelProperty(value = "平台类型", example = "IOS")
    @NotNull(message = "平台类型不能为空")
    private PlatformTypeEnum type;

    @ApiModelProperty(value = "下载链接", example = "http://xxxxxx")
    @NotBlank(message = "下载链接不能为空")
    @URL
    private String downloadUrl;

    @ApiModelProperty(value = "是否测试", example = "false")
    private Boolean test = false;

    @ApiModelProperty(value = "备注", example = "XXXXXX")
    @Size(max = 200)
    private String notes;

    @ApiModelProperty(value = "是否启用", example = "true")
    @NotNull(message = "是否启用不能为空")
    private Boolean enabled;

    @ApiModelProperty(value = "强制下载", example = "true")
    private Boolean forcedDownload;

    private String attribute1;

    private String attribute2;

    private String attribute3;

    private String attribute4;

    private String attribute5;

}
