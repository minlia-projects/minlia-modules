package com.minlia.modules.aliyun.sesame.service;

import com.minlia.modules.aliyun.sesame.body.SesameVerificationRequestBody;
import com.minlia.modules.aliyun.sesame.body.SesameVerificationResponseBody;


public interface SesameVerificationService {

    /**
     * 创建
     * @param requestBody
     * @return
     */
    SesameVerificationResponseBody verification(SesameVerificationRequestBody requestBody);

}
