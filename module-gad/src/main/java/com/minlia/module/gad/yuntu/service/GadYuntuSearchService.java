package com.minlia.module.gad.yuntu.service;

import com.minlia.module.gad.yuntu.body.*;

/**
 * Created by garen on 2017/12/27.
 */
public interface GadYuntuSearchService {

    /**
     * 本地检索
     * @param body
     * @return
     */
    GadYuntuSearchResponseBody searchLocal(GadYuntuSearchLocalRequestBody body);

    /**
     * 周边检索
     * @param body
     * @return
     */
    GadYuntuSearchResponseBody searchAround(GadYuntuSearchAroundRequestBody body);

    /**
     * id检索
     * @param id
     * @return
     */
    GadYuntuSearchResponseBody searchId(Long id);

    /**
     * 条件查询
     * @param body
     * @return
     */
    GadYuntuSearchResponseBody searchList(GadYuntuSearchListRequestBody body);

    /**
     * 省数据分布检索
     * @param body
     * @return
     */
    GadYuntuSearchResponseBody searchProvince(GadYuntuSearchProvinceRequestBody body);

    /**
     * 市数据分布检索请求
     * @param body
     * @return
     */
    GadYuntuSearchResponseBody searchCity(GadYuntuSearchCityRequestBody body);

    /**
     * 区县数据分布检索
     * @param body
     * @return
     */
    GadYuntuSearchResponseBody searchDistrict(GadYuntuSearchDistrictRequestBody body);

    /**
     * 附近检索接口
     * @param body
     * @return
     */
    GadYuntuSearchResponseBody searchNearbyAround(GadYuntuSearchNearbyRequestBody body);

}
