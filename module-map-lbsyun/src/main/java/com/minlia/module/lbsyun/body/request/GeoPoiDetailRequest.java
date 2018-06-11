package com.minlia.module.lbsyun.body.request;

import com.minlia.cloud.body.ApiRequestBody;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 查询指定id的数据（poi） 请求体
 * Created by garen on 2018/6/9.
 */
@Data
public class GeoPoiDetailRequest implements ApiRequestBody{

    /**
     * 必选  申请ak
     */
    @NotBlank
    private String ak;

    @NotNull
    private Long geotable_id;

    /**
     * 位置数据对应的id
     */
    @NotNull
    private Long id;

}
