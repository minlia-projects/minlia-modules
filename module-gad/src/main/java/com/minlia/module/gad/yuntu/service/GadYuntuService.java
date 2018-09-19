package com.minlia.module.gad.yuntu.service;

import com.google.gson.JsonObject;
import com.minlia.module.gad.yuntu.body.*;

/**
 * Created by garen on 2017/12/27.
 */
public interface GadYuntuService {

    //创建表请求地址
    public static String create_table_url = "http://yuntuapi.amap.com/datamanage/table/create";
    //创建数据请求地址（单条）
    public static String create_data_url = "http://yuntuapi.amap.com/datamanage/data/create";
    //创建数据请求地址（批量）
    public static String create_data_batch_url = "http://yuntuapi.amap.com/datamanage/data/batchcreate";
    //更新数据请求地址（单条）
    public static String update_data_url = "http://yuntuapi.amap.com/datamanage/data/update";
    //删除数据请求地址（单条/批量）一次请求限制1-50条数据，多个_id用逗号隔开
    public static String delete_data_url = "http://yuntuapi.amap.com/datamanage/data/delete";

    //本地检索请求地址
    public static String data_search_local_url = "http://yuntuapi.amap.com/datasearch/local";
    //周边检索请求地址
    public static String data_search_around_url = "http://yuntuapi.amap.com/datasearch/around";
    //d检索请求地址
    public static String data_search_id_url = "http://yuntuapi.amap.com/datasearch/id";
    //条件查询请求地址
    public static String data_search_list_url = "http://yuntuapi.amap.com/datamanage/data/list";
    //省数据分布检索请求地址
    public static String data_search_province_url = "http://yuntuapi.amap.com/datasearch/statistics/province";
    //市数据分布检索请求
    public static String data_search_city_url = "http://yuntuapi.amap.com/datasearch/statistics/city";
    //区县数据分布检索请求
    public static String data_search_district_url = "http://yuntuapi.amap.com/datasearch/statistics/district";
    //附近检索接口
    public static String data_search_nearby_around_url = "http://yuntuapi.amap.com/nearby/around";

    GadYuntuResponseBody createData(JsonObject json);

    GadYuntuResponseBody updateData(JsonObject json);

    GadYuntuDeleteResponseBody deleteData(String ids);

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
    GadYuntuSearchResponseBody searchNearbyAround(GadYuntuSearchDistrictRequestBody body);

}
