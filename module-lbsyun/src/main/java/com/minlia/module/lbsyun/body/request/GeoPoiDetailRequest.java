package com.minlia.module.lbsyun.body.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 查询指定id的数据（poi） 请求体
 * Created by garen on 2018/6/9.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoPoiDetailRequest extends GeoRequest{

    /**
     * 位置数据对应的id
     */
    @NotNull
    private Long id;

}
