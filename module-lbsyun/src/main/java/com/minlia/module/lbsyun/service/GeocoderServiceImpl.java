package com.minlia.module.lbsyun.service;

import com.google.gson.Gson;
import com.minlia.module.lbsyun.body.request.GeoCoderRequest;
import com.minlia.module.lbsyun.body.request.GeoRequest;
import com.minlia.module.lbsyun.body.response.GeoCoderResponse;
import com.minlia.module.lbsyun.config.LbsProperties;
import com.minlia.modules.http.GetParamter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by garen on 2018/6/13.
 */
@Service
public class GeocoderServiceImpl implements GeocoderService{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LbsProperties lbsProperties;

    //逆地理编码服务
//    用户可通过该功能，将位置坐标解析成对应的行政区划数据以及周边高权重地标地点分布情况，整体描述坐标所在的位置。
    private static String regeo_url = "http://api.map.baidu.com/geocoder/v2/";

    @Override
    public GeoCoderResponse regeo(GeoCoderRequest request) {
        return this.get(regeo_url,request);
    }

    private GeoCoderResponse get(String url, GeoRequest request) {
        if (null == request.getAk()) {
            request.setAk(lbsProperties.getAk());
        }
        url = GetParamter.getUrl1(url,request);
        String response = restTemplate.getForObject(url,String.class);
        return new Gson().fromJson(response, GeoCoderResponse.class);
    }

}
