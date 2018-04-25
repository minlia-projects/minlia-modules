package com.minlia.module.map.district.service;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.module.map.district.body.GadDistrictQueryRequestBody;
import com.minlia.module.map.district.entity.GadDistrict;

import java.util.List;

/**
 * Created by Calvin On 2017/12/14.
 */
public interface GadDistrictService {
    /**
     * 获取所有行政区域
     * @return
     */
    StatefulBody initAllDstrict();

    /**
     * 是否存在
     * @param body
     * @return
     */
    boolean exists(GadDistrictQueryRequestBody body);

    /**
     * 查询数量
     * @param body
     * @return
     */
    long count(GadDistrictQueryRequestBody body);

    GadDistrict queryById(Long id);

    GadDistrict queryIdAndNotNull(Long id);

    List<GadDistrict> queryList(GadDistrictQueryRequestBody requestBody);

}
