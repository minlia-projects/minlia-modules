package com.minlia.module.lbsyun.body.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * 矩形检索请求体
 * Created by garen on 2018/6/9.
 */
@Data
public class GeoSearchBoundRequest extends GeoSearchRequest{

    /**
     * 矩形区域
     * 左下角和右上角的经纬度坐标点。2个点用;号分隔
     * 样例：116.30,36.20;117.30,37.20
     */
    @NotBlank
    @Size(max = 25)
    private String bounds;

}
