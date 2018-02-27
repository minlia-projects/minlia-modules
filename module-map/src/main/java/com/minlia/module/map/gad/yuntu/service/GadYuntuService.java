package com.minlia.module.map.gad.yuntu.service;

import com.google.gson.JsonObject;
import com.minlia.module.map.gad.yuntu.body.*;

/**
 * Created by garen on 2017/12/27.
 */
public interface GadYuntuService {

//    void setYuntuConfig(GadYuntuConfig yuntuConfig);

    GadYuntuResponseBody createData(JsonObject json);

    GadYuntuResponseBody updateData(JsonObject json);

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

}
