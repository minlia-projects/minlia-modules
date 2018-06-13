package com.minlia.module.lbsyun.body.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * 周边检索请求体
 * Created by garen on 2018/6/9.
 */
@Data
public class GeoSearchNearbyRequest extends GeoSearchRequest{

    /**
     * 检索的中心点
     * 样例：116.4321,38.76623
     */
    @NotBlank
    @Size(max = 25)
    private String location;

    /**
     * 检索半径	单位为米，默认为1000
     */
    private Integer radius;

}
