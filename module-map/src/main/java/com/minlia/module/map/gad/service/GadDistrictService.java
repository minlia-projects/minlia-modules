package com.minlia.module.map.gad.service;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.module.map.gad.body.GadDistrictQueryRequestBody;
import com.minlia.module.map.gad.domain.GadDistrict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    GadDistrict queryOne(Long id);

    GadDistrict queryOneAndNotNull(Long id);

    List<GadDistrict> queryList(GadDistrictQueryRequestBody requestBody);

}
