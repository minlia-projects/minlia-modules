package com.kdniao.endpoint.service;

import com.kdniao.endpoint.requestBody.TrackQueryRequestBody;

/**
 * Created by qianyi on 2017/9/6.
 */
public interface KdnTrackQueryService {

    /**
     *  快递鸟,快递订单实时查询
     * @param trackQueryRequestBody 实时查询请求体,包括快递公司编码和快递单号
     * @return
     */
    public String  trackQuery(TrackQueryRequestBody trackQueryRequestBody);
}
