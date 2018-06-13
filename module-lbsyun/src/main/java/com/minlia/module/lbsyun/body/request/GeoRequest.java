package com.minlia.module.lbsyun.body.request;

import com.minlia.cloud.body.ApiRequestBody;
import lombok.Data;

/**
 * 查询指定id的数据（poi） 请求体
 * Created by garen on 2018/6/9.
 */
@Data
public class GeoRequest implements ApiRequestBody{

    /**
     * 用户的访问权限key
     * 必选、string(50)
     */
    private String ak;

    /**
     * 创建数据的对应数据表id
     * 必选
     */
    private String geotable_id;

}
