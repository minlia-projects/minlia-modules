package com.minlia.module.advertisement.bean;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.advertisement.enums.SysAdvertisementTypeEnum;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author garen
 */
@ApiModel(value = "AdvertisementSro")
@Data
public class SysAdvertisementSro implements ApiRequestBody {

    @ApiModelProperty(value = "ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "父ID", example = "1")
    @NotNull
    private Long aid;

    @ApiModelProperty(value = "名称", example = "首页")
    @NotBlank(message = "名称不能为空")
    @Size(max = 50)
    private String name;

    @ApiModelProperty(value = "链接类型", example = "URL")
    @NotNull(message = "类型不能为空")
    private SysAdvertisementTypeEnum type;

    @ApiModelProperty(value = "链接地址", example = "https://xxx.com")
    @NotNull
    @Size(max = 255)
    private String path;

    @ApiModelProperty(value = "封面", example = "封面")
    @NotNull(message = "封面不能为空")
    @Size(max = 255)
//    @URL
    private String cover;

    @ApiModelProperty(value = "备注", example = "XXXXXX")
    @Size(max = 255)
    private String remark;

    @ApiModelProperty(value = "语言")
    @NotNull
    private LocaleEnum locale;

    @ApiModelProperty(value = "是否禁用")
    private Boolean disFlag;

    @Size(max = 255)
    private String attribute1;

    @Size(max = 255)
    private String attribute2;

    @Size(max = 255)
    private String attribute3;

    @Size(max = 255)
    private String attribute4;

    @Size(max = 255)
    private String attribute5;

}
