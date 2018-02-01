package com.minlia.module.aliyun.sesame.service;

import com.minlia.module.aliyun.sesame.body.SesameVerificationRequestBody;
import com.minlia.module.aliyun.sesame.body.SesameVerificationResponseBody;


public interface SesameVerificationService {

    /**
     * 创建
     * @param requestBody
     * @return
     */
    SesameVerificationResponseBody verification(SesameVerificationRequestBody requestBody);

}
