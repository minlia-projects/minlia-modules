package com.minlia.module.map.geocode.service;

import com.minlia.module.map.geocode.body.GadRegeoRequestBody;
import com.minlia.module.map.geocode.body.response.GadRegeoResponseBody;

/**
 * 地理编码
 * Created by garen on 2018/5/11.
 */
public interface GadGeocodeService {

    /**
     * 逆地理编码
     * @param requestBody
     * @return
     */
    GadRegeoResponseBody regeo(GadRegeoRequestBody requestBody);

}
