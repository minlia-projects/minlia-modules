package com.minlia.aliyun.green.config;

import com.minlia.module.common.annotation.ConfigAutowired;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigAutowired(type = "ALI_GREEN")
public class AliyunGreenConfig {

    /**
     * 访问ID
     */
    private String accessKeyId;

    /**
     * 访问密钥
     */
    private String accessKeySecret;

}