package com.minlia.module.disrtict.bean.qo;

import com.minlia.module.data.bean.QueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Calvin On 2017/12/14.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "地图区域-查询")
public class DistrictQO extends QueryRequest {

    @ApiModelProperty(name = "区域编码", example = "440000")
    private String adcode;

    @ApiModelProperty(name = "城市编码", example = "0755")
    private String citycode;

    @ApiModelProperty(name = "区域名称", example = "广东省")
    private String name;

    @ApiModelProperty(name = "经纬度", example = "12325.00,22.11")
    private String center;

    @ApiModelProperty(name = "区域等级", example = "province")
    private String level;

    @ApiModelProperty(name = "详细地址", example = "广东省深圳市龙岗区坂田街道")
    private String address;

    @ApiModelProperty(name = "所有的编码", example = "440000,44011,440053")
    private String fullcode;

    @ApiModelProperty(name = "父类编码", example = "10000")
    private String parent;

}





