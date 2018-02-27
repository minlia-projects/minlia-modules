package com.minlia.module.map.gad.constants;

/**
 * Created by Calvin On 2017/12/17.
 * 高德地图
 */
public class GadConstants {

    /**
     * 高德地图 WebApi_key
     */
    public static final String WEB_API_MAP_KEY = "fa0889a5259072ff43a169e8c87cc560";

    /**
     * 高德地图 小程序_key
     */
    public static final String MINIAPP_MAP_KEY = "178b2f1914203033d8dd1ea0f3ad4745";

    /**
     * 根据key, 获取高德地图 国家-省 URL
     */
    public static final String GAODE_MAP_URL_MAP_KEY = "http://restapi.amap.com/v3/config/district?key=%s";

    /**
     *  获取高德地图-省下的子级、 市下的子级、区下的子级 URL
     */
    public static final String GAODE_MAP_URL_MAP_CHILDREN = "http://restapi.amap.com/v3/config/district?subdistrict=%s&key=%s";

}
