package com.minlia.modules.starter.oss.v2;

import com.aliyun.oss.OSSClient;
import com.minlia.modules.starter.oss.v2.api.config.AliyunMtsProperties;
import com.minlia.modules.starter.oss.v2.api.config.AliyunOssProperties;
import com.minlia.modules.starter.oss.v2.api.service.MtsService;
import com.minlia.modules.starter.oss.v2.api.service.MtsServiceImpl;
import com.minlia.modules.starter.oss.v2.api.service.OssService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConditionalOnClass({OSSClient.class})
@EnableConfigurationProperties({AliyunOssProperties.class, AliyunMtsProperties.class})
public class AliyunOssAutoConfiguration {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private AliyunOssProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public OSSClient ossClient() {
//        OSSClient ossClient = new OSSClient(endpoint,key, secret);
        return new OSSClient(properties.getEndpoint(), properties.getKey(), properties.getSecret());
    }

    @Bean
    @ConditionalOnMissingBean
    public OssService ossService() {
        OssService ossService = new OssService();
        return ossService;
    }

//    @Bean
//    public AliyunOssHealthIndicator aliyunOssHealthIndicator() {
//        return new AliyunOssHealthIndicator();
//    }
//
//    @Bean
//    public AliyunOssMetrics aliyunOssMetrics() {
//        return new AliyunOssMetrics();
//    }
//
//    @Bean
//    public AliyunOssEndpoint aliyunOssEndpoint() {
//        return new AliyunOssEndpoint();
//    }
}
