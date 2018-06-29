package com.minlia.module.wechat.mp.material.service;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.module.wechat.mp.material.util.WxMpRestUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MpMaterialServiceImpl implements MpMaterialService {

    @Override
    public StatefulBody materialGet(String mediaId) {
        Map<String, Object> params = new HashMap<>();
        params.put("media_id", mediaId);
        return SuccessResponseBody.builder().payload(WxMpRestUtils.post(MpMaterialService.MATERIAL_GET_URL,params)).build();
    }

    @Override
    @Cacheable()
    public StatefulBody materialBatchGet(String type, int offset, int count) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("offset", offset);
        params.put("count", count);
        return SuccessResponseBody.builder().payload(WxMpRestUtils.post(MpMaterialService.MATERIAL_BATCHGET_URL,params)).build();
    }

}
