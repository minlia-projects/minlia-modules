package com.minlia.modules.starter.oss.v2.api.service;

import com.minlia.modules.starter.oss.v2.api.body.MtsRequestBody;
import com.minlia.modules.starter.oss.v2.api.body.MtsResponseBody;

/**
 * Created by garen on 2017/8/29.
 */
public interface MtsService {

    MtsResponseBody transcoding(MtsRequestBody requestbody);

}
