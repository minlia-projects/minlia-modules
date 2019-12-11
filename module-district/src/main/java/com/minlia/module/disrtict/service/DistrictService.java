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

    District queryById(Long id);

    long count(DistrictQO qo);

    boolean exists(DistrictQO qo);

    District queryIdAndNotNull(Long id);

    List<District> queryList(DistrictQO qo);

    String selectParentByAdcode(String adcode);

}
