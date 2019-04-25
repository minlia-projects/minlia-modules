package com.minlia.modules.aliyun.sms.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
aliyun:
    sms:
        accessId: LTAIc3r9adaIXwpH
        accessKey: i86XebUFjiDY7cDQVCFFpPQBOUTcZt
        backend: cn-hangzhou
        regionId: cn-hangzhou
        product: Sms
        entity: sms.aliyuncs.com
        signName: 测试
 */
@ConfigurationProperties(
        prefix = "aliyun.sms",
        ignoreUnknownFields = true
)
@Data
public class AliyunSmsProperties {

    private String accessId;
    private String accessKey;
    private String signName;
    private String endpoint;
    private String regionId;

    private String product;//Sms
    private String domain;//sms.aliyuncs.com

}
