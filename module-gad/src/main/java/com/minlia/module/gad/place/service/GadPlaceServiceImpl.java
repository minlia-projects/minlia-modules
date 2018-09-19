package com.minlia.module.gad.place.service;

import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.gad.code.GadCode;
import com.minlia.module.gad.place.body.request.GadPlaceAroundRequestBody;
import com.minlia.module.gad.place.body.response.GadPlaceAroundResponseBody;
import com.minlia.module.gad.yuntu.body.GadYuntuAbstractRequestBody;
import com.minlia.module.gad.yuntu.body.GadYuntuSearchResponseBody;
import com.minlia.module.gad.yuntu.config.GadYuntuConfig;
import com.minlia.module.gad.yuntu.utils.GadUtils;
import com.minlia.modules.http.GetParamter;
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
        ApiAssert.hasLength(yuntuConfig.getWebApiKey(), GadCode.Message.WEB_API_KEY_NOT_FOUND);
        ApiAssert.hasLength(yuntuConfig.getTableId(), GadCode.Message.WEB_TABLE_ID_NOT_FOUND);
        body.setKey(yuntuConfig.getWebApiKey());
        body.setTableid(yuntuConfig.getTableId());
        body.setSig(GadUtils.singMd5(body,yuntuConfig.getWebApiKey()));
        ResponseEntity<GadYuntuSearchResponseBody> responseEntity = restTemplate.getForEntity(GetParamter.getUrl(url,GadUtils.beanToMap(body)),GadYuntuSearchResponseBody.class);
        return responseEntity.getBody();
    }


}
