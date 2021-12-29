package com.minlia.module.advertisement.bean;

import com.minlia.module.data.entity.BaseQueryEntity;
import com.minlia.module.i18n.enums.LocaleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @author garen
 */
@ApiModel(value = "AdvertisementsQro")
@Data
public class SysAdvertisementsQro extends BaseQueryEntity {

    @ApiModelProperty(value = "名称", example = "首页")
    @Size(max = 50)
    private String name;

    @ApiModelProperty(value = "平台", example = "LANDLORD")
    @Size(max = 20)
    private String platform;

    @ApiModelProperty(value = "投放位置", example = "0")
    @Size(max = 50)
    private String position;

    @ApiModelProperty(value = "横纵比", example = "180:90")
    @Size(max = 50)
    private String aspectRatio;

    @ApiModelProperty(value = "备注", example = "XXXXXX")
    @Size(max = 255)
    private String remark;

    @ApiModelProperty(value = "语言")
    private LocaleEnum locale;

    @ApiModelProperty(value = "是否禁用")
    private Boolean disFlag;

}