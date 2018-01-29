package com.minlia.module.data.service;

import com.minlia.module.data.body.SesameVerificationRequestBody;
import com.minlia.module.data.body.SesameVerificationResponseBody;


public interface SesameVerificationService {

    /**
     * 创建
     * @param requestBody
     * @return
     */
    SesameVerificationResponseBody verification(SesameVerificationRequestBody requestBody);

}
