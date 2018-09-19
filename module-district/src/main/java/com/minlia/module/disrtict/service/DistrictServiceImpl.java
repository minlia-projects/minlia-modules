package com.minlia.module.disrtict.service;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.disrtict.bean.domain.District;
import com.minlia.module.disrtict.bean.qo.DistrictQO;
import com.minlia.module.disrtict.mapper.DistrictMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by Calvin On 2017/12/14.
 */
@Service
@Slf4j
public class DistrictServiceImpl implements DistrictService {

    @Value("${gad.web-api-key}")
    public String GAD_WEB_API_MAP_KEY;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public Response initAllDstrict() {
        Map<String, Object> resultMap = restTemplate.getForObject(String.format(GAODE_MAP_URL_MAP_CHILDREN, 4, GAD_WEB_API_MAP_KEY), Map.class);

        if(resultMap.get("status").equals("1")) {
            List<Map<String, Object>> districts = (List<Map<String, Object>>) resultMap.get("districts");
            //获取行政区域级别--国家
            Map<String, Object> parentMap = districts.get(0);
            saveChildrenDistrictByParentDistrict(parentMap,null,null);
        }
        return Response.success();
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
        long count = districtMapper.count(DistrictQO.builder().adcode(adcode).level(level).name(name).build());
        if (count == 0) {
            districtMapper.create(District.builder()
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
    public District queryById(Long id) {
        return districtMapper.queryById(id);
    }

    @Override
    public District queryIdAndNotNull(Long id) {
        District district = districtMapper.queryById(id);
        ApiAssert.notNull(district, SystemCode.Message.DATA_NOT_EXISTS);
        return district;
    }

    @Override
    public boolean exists(DistrictQO qo) {
        return this.count(qo) > 0;
    }

    @Override
    public long count(DistrictQO qo) {
        return districtMapper.count(qo);
    }

    @Override
    public List<District> queryList(DistrictQO qo) {
        return districtMapper.queryList(qo);
    }

}