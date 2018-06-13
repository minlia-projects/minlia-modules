package com.minlia.module.lbsyun.body.request;

import com.minlia.cloud.body.ApiRequestBody;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 创建表（create geotable）接口 请求体
 * Created by garen on 2018/6/9.
 */
@Data
public class GeoSearchDetailRequest extends GeoRequest{

    /**
     * 3代表百度经纬度坐标系统 4代表百度墨卡托系统
     */
    private Integer coord_type;

    /**
     * 用户的权限签名
     * string(50)
     * 可选,若用户所用AK的校验方式为SN校验时该参数必须（SN生成算法）
     * 若AK设置为SN校验，所有云存储接口均需拼写SN参数，为节省篇幅，后续接口不再单独说明。
     */
    private String sn;

    @NotBlank
    private String uid;

}
