package com.minlia.module.lbsyun.service;

import com.google.gson.Gson;
import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.lbsyun.body.request.GeocolumnCreateRequest;
import com.minlia.module.lbsyun.body.request.GeotableCreateRequest;
import com.minlia.module.lbsyun.body.response.GeoResponse;
import com.minlia.module.lbsyun.utils.GadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by garen on 2018/6/9.
 */
@Service
public class GeotableServiceImpl implements GeotableService {

    //创建表、POST请求
    private static String create_table_url = "http://api.map.baidu.com/geodata/v3/geotable/create";

    //创建列（create column）接口
    private static String create_column_url = "http://api.map.baidu.com/geodata/v3/column/create";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public GeoResponse create(GeotableCreateRequest request) {
        return (GeoResponse) this.post(request,create_table_url,GeoResponse.class);
    }

    @Override
    public GeoResponse createColumn(GeocolumnCreateRequest request) {
        return (GeoResponse) this.post(request,create_column_url,GeoResponse.class);
    }

    /**
     * POST请求
     * @param request
     * @param url
     * @return
     */
    private Object post(ApiRequestBody request, String url, Class<?> responseClass){
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        MultiValueMap<String, Object> map = GadUtils.beanToMap(request,new LinkedMultiValueMap<String, Object>());
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map,headers);
        String response = restTemplate.postForObject(url,entity,String.class);
//        response = StringEscapeUtils.unescapeJson(response);
        return new Gson().fromJson(response, responseClass);
    }

}
