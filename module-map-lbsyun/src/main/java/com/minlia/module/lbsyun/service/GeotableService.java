package com.minlia.module.lbsyun.service;

import com.minlia.module.lbsyun.body.request.GeocolumnCreateRequest;
import com.minlia.module.lbsyun.body.request.GeotableCreateRequest;
import com.minlia.module.lbsyun.body.response.GeoResponse;

/**
 * Created by garen on 2018/6/9.
 */
public interface GeotableService {

    /**
     * 创建表（create geotable）接口
     * @param request
     */
    GeoResponse create(GeotableCreateRequest request);

    /**
     * 创建列（create column）接口
     * @param request
     */
    GeoResponse createColumn(GeocolumnCreateRequest request);

}
