package com.minlia.module.lbsyun.service;

import com.google.gson.Gson;
import com.minlia.module.lbsyun.body.request.*;
import com.minlia.module.lbsyun.config.LbsProperties;
import com.minlia.modules.http.GetParamter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by garen on 2018/6/9.
 */
@Service
public class GeoSearchServiceImpl implements GeoSearchService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LbsProperties lbsProperties;

    //周边检索
    private static String nearby_url = "http://api.map.baidu.com/geosearch/v3/nearby";

    //矩形检索
    private static String bound_url = "http://api.map.baidu.com/geosearch/v3/bound";

    //本地检索
    private static String local_url = "http://api.map.baidu.com/geosearch/v3/local";

    //详情检索
    private static String detail_url = "http://api.map.baidu.com/geosearch/v3/detail/";

    @Override
    public Object nearby(GeoSearchNearbyRequest request) {
        if (request.getLocation().length() > 25) {
            String[] locations = request.getLocation().split(",");
            String location = new StringJoiner(",").add(locations[0].substring(0,11)).add(locations[1].substring(0,11)).toString();
            request.setLocation(location);
        }
        return this.get(nearby_url,request);
    }

    @Override
    public Object bound(GeoSearchBoundRequest request) {
        return this.get(bound_url,request);
    }

    @Override
    public Object local(GeoSearchLocalRequest request) {
        return this.get(local_url,request);
    }

    @Override
    public Object detail(GeoSearchDetailRequest request) {
        return this.get(detail_url+request.getUid(),request);
    }

    private Map get(String url, GeoRequest request) {
        if (null == request.getAk()) {
            request.setAk(lbsProperties.getAk());
        }
        if (null == request.getGeotable_id()) {
            request.setGeotable_id(lbsProperties.getGeotableId());
        }
        url = GetParamter.getUrl1(url,request);
        Map response = restTemplate.getForObject(url,Map.class);
        return response;
    }

}
