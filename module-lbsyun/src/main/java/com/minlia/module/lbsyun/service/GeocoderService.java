package com.minlia.module.lbsyun.service;

import com.minlia.module.lbsyun.body.request.GeoCoderRequest;
import com.minlia.module.lbsyun.body.response.GeoCoderResponse;

/**
 * 地理编码
 * Created by garen on 2018/6/13.
 */
public interface GeocoderService {

    /**
     * 逆地理编码
     * @param request
     * @return
     */
    GeoCoderResponse regeo(GeoCoderRequest request);

}
