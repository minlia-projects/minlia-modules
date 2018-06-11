package com.minlia.module.lbsyun.body.response;

import lombok.Data;

import java.util.Map;

/**
 * Created by garen on 2018/6/12.
 */
@Data
public class GeoPoiResponse {

    /**
     * 状态码:0代表成功，其它取值见文档最后状态码说明
     */
    private Integer status;

    /**
     * 状态码描述，成功时返回“OK”，失败返回对应信息
     */
    private String message;

    private Map<String,Object> poi;

}
