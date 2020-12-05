package com.minlia.modules.aliyun.sms.properties;

import com.minlia.module.common.annotation.ConfigAutowired;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * aliyun:
 * sms:
 * accessId: LTAIc3r9adaIXwpH
 * accessKey: i86XebUFjiDY7cDQVCFFpPQBOUTcZt
 * backend: cn-hangzhou
 * regionId: cn-hangzhou
 * product: Sms
 * entity: sms.aliyuncs.com
 * signName: 测试
 */
//@ConfigurationProperties(
//        prefix = "aliyun.sms",
//        ignoreUnknownFields = true
//)
@Data
@Component
@ConfigAutowired(type = "SYS_SMS_ALIYUN_CONFIG")
public class AliyunSmsProperties {

    private String accessKeyId;
    private String accessKeySecret;
    private String signName;
    private String endpoint;
    private String regionId;

    private String product;//Sms
    private String domain;//sms.aliyuncs.com

}
