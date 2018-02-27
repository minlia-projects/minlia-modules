package com.minlia.module.map.tencent.body;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minlia.module.data.body.QueryRequestBody;
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
public class TencentDistrictQueryRequestBody implements QueryRequestBody {

    @ApiModelProperty("编号")
    private String code;

    @ApiModelProperty("所有行政区域的编号")
    private String fullCode;

    @ApiModelProperty("简称")
    private String name;

    @ApiModelProperty("全称")
    private String fullname;

    @ApiModelProperty("拼音")
    private String pinyin;

    @ApiModelProperty("级别")
    private Integer level;

    @ApiModelProperty("具体地址")
    private String address;

    @ApiModelProperty("经度")
    private Double longitude;

    @ApiModelProperty("纬度")
    private Double latitude;

    @JsonIgnore
    private Long createdBy;

}





