package com.minlia.module.lbsyun.service;

import com.minlia.module.lbsyun.body.request.*;

/**
 * Created by garen on 2018/6/9.
 */
public interface GeoSearchService {

    /**
     * 周边检索
     * @param request
     * @return
     */
    Object nearby(GeoSearchNearbyRequest request);

    /**
     * 矩形检索
     * @param request
     * @return
     */
    Object bound(GeoSearchBoundRequest request);

    /**
     * 本地检索
     * @param request
     * @return
     */
    Object local(GeoSearchLocalRequest request);

    /**
     * 详情检索
     * @param request
     * @return
     */
    Object detail(GeoSearchDetailRequest request);

}
