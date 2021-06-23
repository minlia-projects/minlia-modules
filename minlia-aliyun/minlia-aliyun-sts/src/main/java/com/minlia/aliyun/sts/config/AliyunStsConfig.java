package com.minlia.aliyun.sts.config;

import com.minlia.module.common.annotation.ConfigAutowired;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigAutowired(type = "ALIYUN_STS")
public class AliyunStsConfig {

    /**
     * 是否开启
     */
    private Boolean enable;

    /**
     * 接入端点
     * STS接入地址，例如sts.cn-hangzhou.aliyuncs.com。
     */
    private String endpoint;

    /**
     * 访问ID
     */
    private String accessKeyId;

    /**
     * 访问密钥
     */
    private String accessKeySecret;

    /**
     * ARN
     * 填写步骤3获取的角色ARN。
     */
    private String roleArn;

    /**
     * 访问凭证的有效时间:秒
     */
    private Long durationSeconds;

}