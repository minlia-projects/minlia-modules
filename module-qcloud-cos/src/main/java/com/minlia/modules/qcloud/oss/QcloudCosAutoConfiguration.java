package com.minlia.modules.qcloud.oss;

import com.minlia.modules.qcloud.oss.config.QcloudCosConfig;
import com.qcloud.cos.COSClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({COSClient.class})
@EnableConfigurationProperties({QcloudCosConfig.class})
public class QcloudCosAutoConfiguration {

//    @Autowired
//    private QcloudCosConfig cosProperties;
//
//    @Bean
//    @Lazy
//    @ConditionalOnMissingBean
//    public COSClient ossClient() {
//        // 1 初始化用户身份信息(secretId, secretKey)
//        COSCredentials cred = new BasicCOSCredentials(cosProperties.getAccessKey(), cosProperties.getSecretKey());
//        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
//        ClientConfig clientConfig = new ClientConfig(new Region(cosProperties.getRegion()));
//        // 3 生成cos客户端
//        COSClient cosclient = new COSClient(cred, clientConfig);
//        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式w
//        String bucketName = cosProperties.getBucketName();
//        return cosclient;
//    }
//
//    @Bean
//    @Lazy
//    @ConditionalOnMissingBean
//    public QcloudCosService qcloudCosService(COSClient cosClient) {
//        QcloudCosService qcloud1CosService = new QcloudCosServiceImpl(cosClient);
//        return qcloud1CosService;
//    }

}
