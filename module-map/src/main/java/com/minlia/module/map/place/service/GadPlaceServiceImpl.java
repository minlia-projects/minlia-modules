package com.minlia.module.map.place.service;

import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.map.place.body.request.GadPlaceAroundRequestBody;
import com.minlia.module.map.place.body.response.GadPlaceAroundResponseBody;
import com.minlia.module.map.yuntu.body.GadYuntuAbstractRequestBody;
import com.minlia.module.map.yuntu.body.GadYuntuSearchResponseBody;
import com.minlia.module.map.yuntu.config.GadYuntuConfig;
import com.minlia.module.map.yuntu.utils.GadUtils;
import com.minlia.modules.http.GetParamter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by garen on 2018/4/25.
 */
@Service
public class GadPlaceServiceImpl implements GadPlaceService{

    //周边搜索API服务地址：
    public static String place_around_url = "http://restapi.amap.com/v3/place/around";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GadYuntuConfig yuntuConfig;

    @Override
    public GadPlaceAroundResponseBody around(GadPlaceAroundRequestBody requestBody) {
        requestBody.setKey(yuntuConfig.getWebApiKey());
        ResponseEntity<GadPlaceAroundResponseBody> responseEntity = restTemplate.getForEntity(GetParamter.getUrl(place_around_url,GadUtils.beanToMap(requestBody)),GadPlaceAroundResponseBody.class);
        return responseEntity.getBody();
    }

    /**
     * GET
     * @param body
     * @param url
     * @return
     */
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
