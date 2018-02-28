package com.minlia.module.map.gad.yuntu.service;

import com.google.gson.JsonObject;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.map.gad.yuntu.body.*;
import com.minlia.module.map.gad.yuntu.config.GadYuntuConfig;
import com.minlia.module.map.gad.yuntu.utils.GadUtils;
import com.minlia.modules.http.GetParamter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by garen on 2017/12/27.
 */
@Service
@Slf4j
public class GadYuntuServiceImpl implements GadYuntuService{

    //创建表请求地址
    public static String create_table_url = "http://yuntuapi.amap.com/datamanage/table/create";
    //创建数据请求地址（单条）
    public static String create_data_url = "http://yuntuapi.amap.com/datamanage/data/create";
    //创建数据请求地址（批量）
    public static String create_data_batch_url = "http://yuntuapi.amap.com/datamanage/data/batchcreate";
    //更新数据请求地址（单条）
    public static String update_data_url = "http://yuntuapi.amap.com/datamanage/data/update";
    //删除数据请求地址（单条/批量）
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

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private GadYuntuConfig yuntuConfig;

    @Override
    public GadYuntuResponseBody createData(JsonObject json) {
        ApiPreconditions.is(StringUtils.isEmpty(yuntuConfig.getWebApiKey()), ApiCode.NOT_FOUND,"高德地图 web api key 未配置");
        ApiPreconditions.is(StringUtils.isEmpty(yuntuConfig.getTableId()), ApiCode.NOT_FOUND,"高德云图 table id 未配置");

        if (null == json) {
            json = new JsonObject();
            json.addProperty("_name", "但是风格");
            json.addProperty("_location", "107.394729,31.125698");
            json.addProperty("coordtype", "autonavi");
            json.addProperty("_address", "北京市朝阳区望京阜通东大街6号院3号楼adsfsda");
            json.addProperty("type", "楼栋、单套、房间");
            json.addProperty("status", "未租");
            json.addProperty("rental", "9999.99");
            json.addProperty("number", "R1324679");
            json.addProperty("image", "http://mini-q.oss-cn-shenzhen.aliyuncs.com/assets/face-mask.png");
        }

        GadYuntuSingleDataRequestBody body = new GadYuntuSingleDataRequestBody();
        body.setKey(yuntuConfig.getWebApiKey());
        body.setTableid(yuntuConfig.getTableId());
        body.setLoctype(1);
        body.setData(json.toString());
        body.setSig(GadUtils.singMd5(body,yuntuConfig.getWebApiKey()));

        MultiValueMap<String, Object> map = GadUtils.beanToMap(body,new LinkedMultiValueMap<String, Object>());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map ,headers);
        ResponseEntity<GadYuntuResponseBody> responseEntity = restTemplate.postForEntity(create_data_url,entity,GadYuntuResponseBody.class);
        return responseEntity.getBody();
    }

    @Override
    public GadYuntuResponseBody updateData(JsonObject json) {
        if (null == json) {
            json = new JsonObject();
            json.addProperty("_id", "20");
            json.addProperty("_name", "百瑞达大厦");
        }
        GadYuntuResponseBody responseBody = request(json,update_data_url);
        return responseBody;
    }

    @Override
    public GadYuntuSearchResponseBody searchLocal(GadYuntuSearchLocalRequestBody body) {
        if (null == body.getSortrule()) {
            body.setSortrule("name:0");
        }
        return request(body,data_search_local_url);
    }

    @Override
    public GadYuntuSearchResponseBody searchAround(GadYuntuSearchAroundRequestBody body) {
        if (null == body.getKeywords()) {
            body.setKeywords(" ");
        }
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
        if (null == body.getKeywords()) {
            body.setKeywords(" ");
        }
        return request(body,data_search_province_url);
    }

    private GadYuntuResponseBody request(JsonObject json,String url){
        ApiPreconditions.is(StringUtils.isEmpty(yuntuConfig.getWebApiKey()), ApiCode.NOT_FOUND,"高德地图 web api key 未配置");
        ApiPreconditions.is(StringUtils.isEmpty(yuntuConfig.getTableId()), ApiCode.NOT_FOUND,"高德云图 table id 未配置");

        GadYuntuSingleDataRequestBody body = new GadYuntuSingleDataRequestBody();
        body.setKey(yuntuConfig.getWebApiKey());
        body.setTableid(yuntuConfig.getTableId());
        body.setLoctype(1);
        body.setData(json.toString());
        body.setSig(GadUtils.singMd5(body,yuntuConfig.getWebApiKey()));

//        MultiValueMap<String, Object> map = GadUtils.beanToMap(body,new LinkedMultiValueMap<String, Object>());
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map ,headers);
//        ResponseEntity<GadYuntuResponseBody> responseEntity = restTemplate.postForEntity(url,entity,GadYuntuResponseBody.class);

        ResponseEntity<GadYuntuResponseBody> responseEntity = restTemplate.getForEntity(GetParamter.getUrl(url,GadUtils.beanToMap(body)),GadYuntuResponseBody.class);
        return responseEntity.getBody();
    }

    private GadYuntuSearchResponseBody request(GadYuntuAbstractRequestBody body, String url){
        ApiPreconditions.is(StringUtils.isEmpty(yuntuConfig.getWebApiKey()), ApiCode.NOT_FOUND,"高德地图 web api key 未配置");
        ApiPreconditions.is(StringUtils.isEmpty(yuntuConfig.getTableId()), ApiCode.NOT_FOUND,"高德云图 table id 未配置");

        body.setKey(yuntuConfig.getWebApiKey());
        body.setTableid(yuntuConfig.getTableId());
        body.setSig(GadUtils.singMd5(body,yuntuConfig.getWebApiKey()));
        ResponseEntity<GadYuntuSearchResponseBody> responseEntity = restTemplate.getForEntity(GetParamter.getUrl(url,GadUtils.beanToMap(body)),GadYuntuSearchResponseBody.class);
        return responseEntity.getBody();
    }

}
