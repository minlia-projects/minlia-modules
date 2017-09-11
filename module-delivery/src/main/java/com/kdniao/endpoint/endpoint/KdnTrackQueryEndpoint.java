package com.kdniao.endpoint.endpoint;

import com.kdniao.endpoint.requestBody.TrackQueryRequestBody;
import com.kdniao.endpoint.service.KdnTrackQueryService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Created by qianyi on 2017/9/6.
 */
@RequestMapping(value = "kdn")
public class KdnTrackQueryEndpoint {


    private KdnTrackQueryService kdnTrackQueryService;

    /**
     * Json方式 查询订单物流轨迹
     *
     * @throws Exception
     */
    @RequestMapping("/trackQuery")
    public String getOrderTracesByJson(@RequestBody @Valid TrackQueryRequestBody requestBody) throws Exception {
        //校验参数
        String t = kdnTrackQueryService.trackQuery(requestBody);
        //根据公司业务处理返回的信息......
        return t;
    }


}
