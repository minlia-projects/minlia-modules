package com.minlia.module.map.geocode.service;

import com.minlia.module.map.geocode.body.GadRegeoRequestBody;
import com.minlia.module.map.geocode.body.response.GadRegeoResponseBody;
import com.minlia.module.map.yuntu.config.GadYuntuConfig;
import com.minlia.module.map.yuntu.utils.GadUtils;
import com.minlia.modules.http.GetParamter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by garen on 2018/5/11.
 */
@Service
public class GadGeocodeServiceImpl implements GadGeocodeService {

    //逆地理编码API服务地址
    public static String regeo_url = "http://restapi.amap.com/v3/geocode/regeo";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GadYuntuConfig yuntuConfig;

    @Override
    public GadRegeoResponseBody regeo(GadRegeoRequestBody requestBody) {
        requestBody.setKey(yuntuConfig.getWebApiKey());
        ResponseEntity<GadRegeoResponseBody> responseEntity = restTemplate.getForEntity(GetParamter.getUrl(regeo_url, GadUtils.beanToMap(requestBody)),GadRegeoResponseBody.class);
        return responseEntity.getBody();
    }

}
