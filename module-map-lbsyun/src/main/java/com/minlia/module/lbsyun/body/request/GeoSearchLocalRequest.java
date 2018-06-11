package com.minlia.module.lbsyun.body.request;

import com.minlia.cloud.body.ApiRequestBody;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * 本地检索请求体
 * Created by garen on 2018/6/9.
 */
@Data
public class GeoSearchLocalRequest extends GeoSearchRequest{

    /**
     * 检索区域名称:市或区的名字，如北京市，海淀区。
     * String(25)
     */
    @Size(max = 25)
    private String region;

}
