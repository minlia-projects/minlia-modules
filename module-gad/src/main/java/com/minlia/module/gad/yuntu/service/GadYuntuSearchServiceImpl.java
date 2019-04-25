package com.minlia.module.gad.yuntu.service;

import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.gad.code.GadCode;
import com.minlia.module.gad.yuntu.body.*;
import com.minlia.module.gad.yuntu.config.GadYuntuConfig;
import com.minlia.module.gad.yuntu.utils.GadUtils;
import com.minlia.modules.http.GetParamter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by garen on 2017/12/27.
 */
@Service
@Slf4j
public class GadYuntuSearchServiceImpl implements GadYuntuSearchService{

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

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private GadYuntuConfig yuntuConfig;

    @Override
    public GadYuntuSearchResponseBody searchLocal(GadYuntuSearchLocalRequestBody body) {
        if (null == body.getSortrule()) {
            body.setSortrule("name:0");
        }
        return request(body,data_search_local_url);
    }

    @Override
    public GadYuntuSearchResponseBody searchAround(GadYuntuSearchAroundRequestBody body) {
        return request(body,data_search_around_url);
    }

    @Override
    public GadYuntuSearchResponseBody searchId(Long id) {
        return request(new GadYuntuSearchIDRequestBody(id),data_search_id_url);
    }

    @Override
    public GadYuntuSearchResponseBody searchList(GadYuntuSearchListRequestBody body) {
        return request(body,data_search_list_url);
    }

    @Override
    public GadYuntuSearchResponseBody searchProvince(GadYuntuSearchProvinceRequestBody body) {
        return request(body,data_search_province_url);
    }

    @Override
    public GadYuntuSearchResponseBody searchCity(GadYuntuSearchCityRequestBody body) {
        return request(body,data_search_city_url);
    }

    @Override
    public GadYuntuSearchResponseBody searchDistrict(GadYuntuSearchDistrictRequestBody body) {
        return request(body,data_search_district_url);
    }

    @Override
    public GadYuntuSearchResponseBody searchNearbyAround(GadYuntuSearchNearbyRequestBody body) {


        http://yuntuapi.amap.com/nearby/around?key=3f7cbf66ce0da2310dc052d4e7e47b04&center=117.500244,40.417801

        body.setKey("803e61def8f4676c37d6f801a3df5c14");
//        ro.setKey("3f7cbf66ce0da2310dc052d4e7e47b04");
        ResponseEntity<GadYuntuSearchResponseBody> responseEntity = restTemplate.getForEntity(GetParamter.getUrl(data_search_nearby_around_url,GadUtils.beanToMap(body)),GadYuntuSearchResponseBody.class);
        return responseEntity.getBody();
    }

    /**
     * GET
     * @param body
     * @param url
     * @return
     */
    private GadYuntuSearchResponseBody request(GadYuntuAbstractRequestBody body, String url){
        ApiAssert.hasLength(yuntuConfig.getWebApiKey(), GadCode.Message.WEB_API_KEY_NOT_FOUND);
        ApiAssert.hasLength(yuntuConfig.getYuntuTableId(), GadCode.Message.WEB_TABLE_ID_NOT_FOUND);

        body.setKey(yuntuConfig.getWebApiKey());
        body.setTableid(yuntuConfig.getYuntuTableId());
        body.setSig(GadUtils.singMd5(body,yuntuConfig.getWebApiKey()));
        log.info("Gad search url:",GetParamter.getUrl(url,GadUtils.beanToMap(body)));
        ResponseEntity<GadYuntuSearchResponseBody> responseEntity = restTemplate.getForEntity(GetParamter.getUrl(url,GadUtils.beanToMap(body)),GadYuntuSearchResponseBody.class);
        return responseEntity.getBody();
    }

}
