package com.minlia.module.map.yuntu.body;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 本地检索请求体
 * Created by garen on 2017/12/27.
 */
@Data
public class GadYuntuSearchNearbyRequestBody implements Serializable {

    /**
     * 请求服务权限标识 必填
     */
    @JsonIgnore
    private String key;

    /**
     * 中心点坐标    必填
     * 规则： lon,lat（经度,维度）， “,”分割，如117.500244, 40.417801 经纬度小数点不超过6位
     *
     */
    @NotNull
    private String center;

    /**
     * 查询半径 可选
     * 取值范围(1,10000]，单位：米，超出取值范围按照10公里返回
     * 缺省值 1000
     */
    private Integer radius;

    /**
     * 返回结果的条数 可选
     * 取值范围 (1,100]
     * 缺省值  30
     */
    private Integer limit;

    /**
     * 搜索radius(半径）值代表的类型   可选
     * 0：直线距离（默认），1：驾车行驶距离
     * 缺省值 0
     */
    private Integer searchtype;

    /**
     * 检索时间范围的描述    可选
     * 规则：[5,86400]，单位：秒 可检索5s~24小时之内上传过数据的用户信息 超过24小时未上传过数据的用户将作为过期数据，无法返回
     * 缺省值  1800
     */
    private Integer timerange;

    /**
     * 数字签名     可选，选择数字签名认证的用户必填
     */
    @JsonIgnore
    private String sig;

}
