package com.minlia.modules.aliyun.oss.api.service;


import com.minlia.modules.aliyun.oss.api.body.MtsRequestBody;
import com.minlia.modules.aliyun.oss.api.body.MtsResponseBody;

/**
 * Created by garen on 2017/8/29.
 */
public interface MtsService {

    MtsResponseBody transcoding(MtsRequestBody requestbody);

}
