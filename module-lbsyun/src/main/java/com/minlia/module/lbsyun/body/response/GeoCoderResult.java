package com.minlia.module.lbsyun.body.response;

import lombok.Data;

import java.util.List;

/**
 * Created by garen on 2018/6/13.
 */
@Data
public class GeoCoderResult {

    /**
     * 经纬度坐标
     */
    private Object location;

    /**
     * 结构化地址信息
     */
    private String formatted_address;

    /**
     * 可信度，描述打点准确度。[0,100]，大于80表示误差低于100m
     */
    private Integer confidence;

    /**
     * 坐标所在商圈信息，如 "人民大学,中关村,苏州街"。最多返回3个。
     */
    private String business;

    /**
     * （注意，国外行政区划，字段仅代表层级）
     */
    private AddressComponent addressComponent;

    /**
     * 周边poi数组
     */
    private List<Object> pois;

    private List<Object> roads;

    private List<Object> poiRegions;

    /**
     * 当前位置结合POI的语义化结果描述。
     */
    private String sematic_description;

    /**
     * 城市id（不再更新）
     */
    private String cityCode;

}
