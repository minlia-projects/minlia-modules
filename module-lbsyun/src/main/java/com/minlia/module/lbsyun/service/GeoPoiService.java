package com.minlia.module.lbsyun.service;

import com.minlia.module.lbsyun.body.request.GeoPoiCreateRequest;
import com.minlia.module.lbsyun.body.request.GeoPoiDeleteRequest;
import com.minlia.module.lbsyun.body.request.GeoPoiDetailRequest;
import com.minlia.module.lbsyun.body.request.GeoPoiUpdateRequest;
import com.minlia.module.lbsyun.body.response.GeoPoiResponse;
import com.minlia.module.lbsyun.body.response.GeoResponse;
import org.springframework.util.MultiValueMap;

/**
 * Created by garen on 2018/6/11.
 */
public interface GeoPoiService {

    GeoResponse create(GeoPoiCreateRequest request, MultiValueMap<String,Object> keys);

    GeoResponse update(GeoPoiUpdateRequest request, MultiValueMap<String,Object> keys);

    GeoResponse delete(GeoPoiDeleteRequest request, MultiValueMap<String, Object> keys);

    /**
     * 查询指定id的数据
     * @param request
     * @return
     */
    GeoPoiResponse detail(GeoPoiDetailRequest request);

}
