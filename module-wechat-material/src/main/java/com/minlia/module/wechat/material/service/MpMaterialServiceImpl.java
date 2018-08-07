package com.minlia.module.wechat.material.service;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.module.wechat.material.dto.WxMpMaterialBatchGetResult;
import com.minlia.module.wechat.material.mapper.MaterialMapper;
import com.minlia.module.wechat.material.util.WxMpRestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MpMaterialServiceImpl implements MpMaterialService {

    @Autowired
    private MaterialMapper materialMapper;

    @Override
    public void init(String type, Integer offset, Integer count) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("offset", offset);
        params.put("count", count);
        WxMpMaterialBatchGetResult result = WxMpRestUtils.post(MATERIAL_BATCHGET_URL, params);
        materialMapper.create(result);
    }

    @Override
//    @Cacheable(value = "mp_material:id", key = "'mp_material_id:' + #p0")
    public StatefulBody materialGet(String mediaId) {
        Map<String, Object> params = new HashMap<>();
        params.put("media_id", mediaId);
        return SuccessResponseBody.builder().payload(WxMpRestUtils.post(MATERIAL_GET_URL, params)).build();
    }

    @Override
//    @Cacheable(value = "mp_material:page", key = "'mp_material_page:'.concat(#p1.toString()).concat('_').concat(#p1.toString()).concat(#p2.toString())")
    public StatefulBody materialBatchGet(String type, Integer offset, Integer count) {
        WxMpMaterialBatchGetResult result = new WxMpMaterialBatchGetResult();
        result.setItem(materialMapper.queryList());
        return SuccessResponseBody.builder().payload(result).build();
    }

}
