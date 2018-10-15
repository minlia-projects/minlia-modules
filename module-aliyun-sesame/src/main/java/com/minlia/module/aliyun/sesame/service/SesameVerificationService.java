package com.minlia.module.aliyun.sesame.service;

import com.minlia.module.aliyun.sesame.body.SesameVerificationRequest;
import com.minlia.module.aliyun.sesame.body.SesameVerificationResponse;


public interface SesameVerificationService {

    /**
     * 创建
     * @param requestBody
     * @return
     */
    SesameVerificationResponse verification(SesameVerificationRequest requestBody);

}
