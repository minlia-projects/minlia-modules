package com.minlia.module.lbsyun.service;

import com.google.gson.Gson;
import com.minlia.module.lbsyun.body.request.*;
import com.minlia.module.lbsyun.body.response.GeoPoiResponse;
import com.minlia.module.lbsyun.body.response.GeoResponse;
import com.minlia.module.lbsyun.config.LbsProperties;
import com.minlia.module.lbsyun.utils.GadUtils;
import com.minlia.modules.http.GetParamter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by garen on 2018/6/11.
 */
@Service
public class GeoPoiServiceImpl implements GeoPoiService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LbsProperties lbsProperties;

    //创建数据（create poi）接口
    private static String create_url = "http://api.map.baidu.com/geodata/v3/poi/create";

    //修改数据（update poi）列表接口
    private static String update_url = "http://api.map.baidu.com/geodata/v3/poi/update";

    //删除数据（delete poi）列表接口
    private static String delete_url = "http://api.map.baidu.com/geodata/v3/poi/delete";

    //查询指定id的数据（poi）列表接口
    private static String detail_url = "http://api.map.baidu.com/geodata/v3/poi/detail";

    //查询指定条件的数据（poi）列表接口
    private static String list_url = "http://api.map.baidu.com/geodata/v3/poi/list";

    @Override
    public GeoResponse create(GeoPoiCreateRequest request, MultiValueMap<String,Object> keys) {
        return (GeoResponse) this.post(create_url,request,keys,GeoResponse.class);
    }

    @Override
    public GeoResponse update(GeoPoiUpdateRequest request, MultiValueMap<String, Object> keys) {
        return (GeoResponse) this.post(update_url,request,keys,GeoResponse.class);
    }

    @Override
    public GeoResponse delete(GeoPoiDeleteRequest request, MultiValueMap<String, Object> keys) {
        return (GeoResponse) this.post(delete_url,request,keys,GeoResponse.class);
    }

    @Override
    public GeoPoiResponse detail(GeoPoiDetailRequest request) {
        String url = GetParamter.getUrl1(detail_url,request);
        String response = restTemplate.getForObject(url,String.class);
        return new Gson().fromJson(response, GeoPoiResponse.class);
    }

    /**
     * POST请求
     * @param request
     * @param keys
     * @param url
     * @return
     */
    private Object post(String url, GeoRequest request, MultiValueMap<String, Object> keys, Class<?> responseClass){
        if (null == request.getAk()) {
            request.setAk(lbsProperties.getAk());
        }
        if (null == request.getGeotable_id()) {
            request.setGeotable_id(lbsProperties.getGeotableId());
        }
        MultiValueMap<String, Object> map = GadUtils.beanToMap(request,new LinkedMultiValueMap<String, Object>());
        if (null != keys) {
            map.putAll(keys);
        }
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map,headers);
        String response = restTemplate.postForObject(url,entity,String.class);
        return new Gson().fromJson(response, responseClass);
    }

}
