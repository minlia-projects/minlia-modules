package com.minlia.module.map.gad.service;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.map.common.constant.DistrictApiCode;
import com.minlia.module.map.gad.body.GadDistrictQueryRequestBody;
import com.minlia.module.map.gad.constants.GadConstants;
import com.minlia.module.map.gad.domain.GadDistrict;
import com.minlia.module.map.gad.repository.GadDistrictMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by Calvin On 2017/12/14.
 */
@Service
@Slf4j
public class GadDistrictServiceImpl implements GadDistrictService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GadDistrictMapper gadDistrictMapper;

    @Override
    public StatefulBody initAllDstrict() {
        Map<String, Object> resultMap = restTemplate.getForObject(String.format(GadConstants.GAODE_MAP_URL_MAP_CHILDREN, 4, GadConstants.WEB_API_MAP_KEY), Map.class);

        if(resultMap.get("status").equals("1")) {
            List<Map<String, Object>> districts = (List<Map<String, Object>>) resultMap.get("districts");
            //获取行政区域级别--国家
            Map<String, Object> parentMap = districts.get(0);
            saveChildrenDistrictByParentDistrict(parentMap,null,null);
        }
        return SuccessResponseBody.builder().code(1).message("初始化完成").build();
    }


    /**
     * 根据父级保存子级下的行政区域
     * @param mapResultSet
     * @param parentCode
     * @param parentAddress
     */
    private void saveChildrenDistrictByParentDistrict(Map<String, Object> mapResultSet,String parentCode,String parentAddress){
        List<Map<String, Object>> districts = (List<Map<String, Object>>) mapResultSet.get("districts");
        for (Map<String, Object> districtMap : districts) {
            saveMapDistrict(districtMap,parentCode, parentAddress);
            saveChildrenDistrictByParentDistrict(districtMap,(null == parentCode ? "" : parentCode + ",") + districtMap.get("adcode"), (null == parentAddress ? "" : parentAddress + ",") + districtMap.get("name"));
        }
    }

    /**
     * 保存地图上的行政区域
     * @param districtMap
     * @param parentCode
     * @param parentAddress
     */
    private void saveMapDistrict(Map<String, Object> districtMap,String parentCode,String parentAddress) {
        String adcode = (String) districtMap.get("adcode");
        String level = (String) districtMap.get("level");
        String name = (String) districtMap.get("name");
        long count = gadDistrictMapper.count(GadDistrictQueryRequestBody.builder().adcode(adcode).level(level).name(name).build());
        if (count == 0) {
            gadDistrictMapper.create(GadDistrict.builder()
                    .adcode(adcode)
                    .name(name)
                    .level(level)
                    .citycode(districtMap.get("citycode").toString())
                    .center((String) districtMap.get("center"))
                    .address((null == parentAddress ? "" : parentAddress) + (String) districtMap.get("name"))
                    .fullcode(districtMap.get("level").equals("street") ? parentCode : (null == parentCode ? "" : parentCode + ",") + (String) districtMap.get("adcode"))
                    .parent(null == parentCode ? null : parentCode.substring(parentCode.lastIndexOf(",") + 1, parentCode.length()))
                    .build());
        }
    }

    @Override
    public boolean exists(GadDistrictQueryRequestBody body) {
        return this.count(body) > 0;
    }

    @Override
    public long count(GadDistrictQueryRequestBody requestBody) {
        return gadDistrictMapper.count(requestBody);
    }

    @Override
    public GadDistrict queryOne(Long id) {
        return gadDistrictMapper.queryOne(id);
    }

    @Override
    public GadDistrict queryOneAndNotNull(Long id) {
        GadDistrict district = gadDistrictMapper.queryOne(id);
        ApiPreconditions.is(null == district, DistrictApiCode.NOT_EXISTS,"区域不存在");
        return district;
    }

    @Override
    public List<GadDistrict> queryList(GadDistrictQueryRequestBody requestBody) {
        return gadDistrictMapper.queryList(requestBody);
    }

}