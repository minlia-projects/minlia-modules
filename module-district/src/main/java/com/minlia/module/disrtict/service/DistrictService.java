package com.minlia.module.disrtict.service;

import com.minlia.cloud.body.Response;
import com.minlia.module.disrtict.bean.domain.District;
import com.minlia.module.disrtict.bean.qo.DistrictQO;

import java.util.List;

/**
 * Created by Calvin On 2017/12/14.
 */
public interface DistrictService {

    /**
     *  获取高德地图-省下的子级、 市下的子级、区下的子级 URL
     */
    String GAODE_MAP_URL_MAP_CHILDREN = "http://restapi.amap.com/v3/config/district?subdistrict=%s&key=%s";

    /**
     * 获取所有行政区域
     * @return
     */
    Response initAllDstrict();

    /**
     * 是否存在
     * @param body
     * @return
     */
    boolean exists(DistrictQO body);

    /**
     * 查询数量
     * @param body
     * @return
     */
    long count(DistrictQO body);

    District queryById(Long id);

    District queryIdAndNotNull(Long id);

    List<District> queryList(DistrictQO requestBody);

}
