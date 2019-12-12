package com.minlia.modules.aliyun.oss;

import com.aliyun.oss.OSSClient;
import com.minlia.modules.aliyun.oss.api.config.AliyunMtsProperties;
import com.minlia.modules.aliyun.oss.api.service.OssService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({OSSClient.class})
@EnableConfigurationProperties({AliyunMtsProperties.class})
public class AliyunOssAutoConfiguration {

//    @Bean
//    @ConditionalOnMissingBean
//    public OSSClient ossClient() {
//        return new OSSClient(properties.getEndpoint(), properties.getKey(), properties.getSecret());
//    }

    @Bean
    @ConditionalOnMissingBean
    public OssService ossService() {
        OssService ossService = new OssService();
        return ossService;
    }

}
