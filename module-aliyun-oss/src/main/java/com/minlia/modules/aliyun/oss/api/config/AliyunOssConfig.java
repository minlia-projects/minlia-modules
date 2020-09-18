package com.minlia.modules.aliyun.oss.api.config;

import com.minlia.module.common.annotation.ConfigAutowired;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 阿里云OSS配置
 *
 * @author garen
 * @date 2018/7/12
 */
@Component
@ConfigAutowired(type = "SYS_OSS_ALIYUN_CONFIG")
@Data
public class AliyunOssConfig {

    private String accessKeyId;

    private String accessKeySecret;

    private String bucket;

    private String endpoint;

    /**
     * 域名
     */
    private String domain;

}
//ACCESS_KEY_ID;
//ACCESS_KEY_SECRET;
//BUCKET;
//ENDPOINT;
//DOMAIN;