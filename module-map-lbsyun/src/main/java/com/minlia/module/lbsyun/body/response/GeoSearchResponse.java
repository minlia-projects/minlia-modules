package com.minlia.module.lbsyun.body.response;

import lombok.Data;

import java.util.List;

/**
 * Created by garen on 2018/6/12.
 */
@Data
public class GeoSearchResponse {

    /**
     * 状态码:0代表成功，其它取值见文档最后状态码说明
     */
    private Integer status;

    /**
     * 分页参数，当前页返回数量
     */
    private Integer size;

    /**
     * 分页参数，所有召回数量
     */
    private Integer total;

    /**
     * 内容
     */
    private List<Object> contents;

}
