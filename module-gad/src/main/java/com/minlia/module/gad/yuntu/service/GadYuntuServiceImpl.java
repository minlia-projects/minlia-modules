package com.minlia.module.gad.yuntu.service;

import com.google.gson.JsonObject;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.gad.code.GadCode;
import com.minlia.module.gad.yuntu.body.*;
import com.minlia.module.gad.yuntu.config.GadYuntuConfig;
import com.minlia.module.gad.yuntu.utils.GadUtils;
import com.minlia.modules.http.GetParamter;
import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GadYuntuConfig yuntuConfig;

    @Override
    public GadYuntuResponseBody createData(JsonObject json) {
        ApiAssert.hasLength(yuntuConfig.getWebApiKey(), GadCode.Message.WEB_API_KEY_NOT_FOUND);
        ApiAssert.hasLength(yuntuConfig.getYuntuTableId(), GadCode.Message.WEB_TABLE_ID_NOT_FOUND);

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
        body.setTableid(yuntuConfig.getYuntuTableId());
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
            json.addProperty("_name", "xxx大厦");
        }
        GadYuntuResponseBody responseBody = request(json,update_data_url);
        return responseBody;
    }

    @Override
    public GadYuntuDeleteResponseBody deleteData(String ids) {
        ApiAssert.hasLength(yuntuConfig.getWebApiKey(), GadCode.Message.WEB_API_KEY_NOT_FOUND);
        ApiAssert.hasLength(yuntuConfig.getYuntuTableId(), GadCode.Message.WEB_TABLE_ID_NOT_FOUND);

        GadYuntuDeleteRequestBody body = new GadYuntuDeleteRequestBody();
        body.setKey(yuntuConfig.getWebApiKey());
        body.setTableid(yuntuConfig.getYuntuTableId());
        body.setIds(ids);
        body.setSig(GadUtils.singMd5(body,yuntuConfig.getWebApiKey()));

        MultiValueMap<String, Object> map = GadUtils.beanToMap(body,new LinkedMultiValueMap<String, Object>());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map ,headers);
        ResponseEntity<GadYuntuDeleteResponseBody> responseEntity = restTemplate.postForEntity(delete_data_url,entity,GadYuntuDeleteResponseBody.class);
        return responseEntity.getBody();
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

    @Override
    public GadYuntuSearchResponseBody searchCity(GadYuntuSearchCityRequestBody body) {
        if (null == body.getKeywords()) {
            body.setKeywords(" ");
        }
        return request(body,data_search_city_url);
    }

    @Override
    public GadYuntuSearchResponseBody searchDistrict(GadYuntuSearchDistrictRequestBody body) {
        if (null == body.getKeywords()) {
            body.setKeywords(" ");
        }
        return request(body,data_search_district_url);
    }

    @Override
    public GadYuntuSearchResponseBody searchNearbyAround(GadYuntuSearchDistrictRequestBody body) {
        return null;
    }

    /**
     * POST请求
     * @param json
     * @param url
     * @return
     */
    private GadYuntuResponseBody request(JsonObject json,String url){
        ApiAssert.hasLength(yuntuConfig.getWebApiKey(), GadCode.Message.WEB_API_KEY_NOT_FOUND);
        ApiAssert.hasLength(yuntuConfig.getYuntuTableId(), GadCode.Message.WEB_TABLE_ID_NOT_FOUND);

        GadYuntuSingleDataRequestBody body = new GadYuntuSingleDataRequestBody();
        body.setKey(yuntuConfig.getWebApiKey());
        body.setTableid(yuntuConfig.getYuntuTableId());
        body.setLoctype(1);
        body.setData(json.toString());
        body.setSig(GadUtils.singMd5(body,yuntuConfig.getWebApiKey()));

        MultiValueMap<String, Object> map = GadUtils.beanToMap(body,new LinkedMultiValueMap<String, Object>());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map ,headers);
        ResponseEntity<GadYuntuResponseBody> responseEntity = restTemplate.postForEntity(url,entity,GadYuntuResponseBody.class);
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
        ResponseEntity<GadYuntuSearchResponseBody> responseEntity = restTemplate.getForEntity(GetParamter.getUrl(url,GadUtils.beanToMap(body)),GadYuntuSearchResponseBody.class);
        return responseEntity.getBody();
    }

}
