package com.minlia.module.lbsyun.body.request;

import com.minlia.cloud.body.ApiRequestBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 创建表（create geotable）接口 请求体
 * Created by garen on 2018/6/9.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoPoiUpdateRequest implements ApiRequestBody{

    private Long id;

    /**
     * 位置数据名称
     */
    @Size(max = 128)
    private String title;

    /**
     * 位置数据地址
     */
    @Size(max = 256)
    private String address;

    /**
     * 位置数据类别
     */
    @Size(max = 200)
    private String tags;

    /**
     * 用户上传的纬度
     */
    private Double latitude;

    /**
     * 用户上传的经度
     */
    private Double longitude;

    /**
     * 用户上传的坐标的类型：1、2、3、4
     * 1：GPS经纬度坐标
     * 2：国测局加密经纬度坐标
     * 3：百度加密经纬度坐标
     * 4：百度加密墨卡托坐标
     */
    private Integer coord_type;

    /**
     * 创建数据的对应数据表id
     * 必选
     */
    private String geotable_id;

    /**
     * 用户的访问权限key
     * 必选、string(50)
     */
    private String ak;

}
