package com.minlia.module.advertisement.bean;

import com.minlia.module.advertisement.enums.SysAdvertisementTypeEnum;
import com.minlia.module.data.entity.BaseQueryEntity;
import com.minlia.module.i18n.enums.LocaleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Size;

/**
 * @author garen
 */
@ApiModel(value = "AdvertisementQro")
@Data
public class SysAdvertisementQro extends BaseQueryEntity {

    @ApiModelProperty(value = "父ID", example = "1")
    private Long aid;

    @ApiModelProperty(value = "名称", example = "首页")
    @Size(max = 50)
    private String name;

    @ApiModelProperty(value = "链接类型", example = "URL")
    private SysAdvertisementTypeEnum type;

    @ApiModelProperty(value = "链接地址", example = "https://xxx.com")
    @Size(max = 255)
    private String path;

    @ApiModelProperty(value = "封面", example = "封面")
    @Size(max = 255)
    @URL
    private String cover;

    @ApiModelProperty(value = "备注", example = "XXXXXX")
    @Size(max = 255)
    private String remark;

    @ApiModelProperty(value = "语言")
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