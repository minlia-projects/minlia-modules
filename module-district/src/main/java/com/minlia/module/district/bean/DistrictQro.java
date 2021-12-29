package com.minlia.module.district.bean;

import com.minlia.module.data.bean.QueryRequest;
import com.minlia.module.district.enumeration.DistrictLevel;
import com.minlia.module.i18n.enums.LocaleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author garen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "地图区域-查询")
public class DistrictQro extends QueryRequest {

    @ApiModelProperty(name = "父类编码", example = "100000")
    private String parent;

    @ApiModelProperty(name = "区域编码", example = "440000")
    private String adcode;

    @ApiModelProperty(name = "区域名称", example = "广东省")
    private String name;

    @ApiModelProperty(name = "区域等级", example = "province")
    private DistrictLevel level;

    @ApiModelProperty(name = "语言", example = "zh_CN")
    private LocaleEnum locale;

}