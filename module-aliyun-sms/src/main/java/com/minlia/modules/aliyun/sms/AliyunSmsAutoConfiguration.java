package com.minlia.modules.aliyun.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.minlia.modules.aliyun.sms.properties.AliyunSmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Slf4j
@Configuration
//@EnableConfigurationProperties(value = {AliyunSmsProperties.class })
@ConditionalOnClass(SingleSendSmsRequest.class)
public class AliyunSmsAutoConfiguration implements EnvironmentAware {

//    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
//        this.propertyResolver = new RelaxedPropertyResolver(environment, "aliyun.sms.");
    }

    @Autowired
    private AliyunSmsProperties value;

    @Bean
    public IAcsClient client(){
        //初始化ascClient需要的几个参数
        final String regionId = value.getRegionId();
        final String endpointName = value.getEndpoint();
        final String product = value.getProduct();//短信API产品名称（短信产品名固定，无需修改）
        final String domain = value.getDomain();//短信API产品域名（接口地址固定，无需修改）
        final String accessKeyId = value.getAccessKeyId();//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = value.getAccessKeySecret();//你的accessKeySecret，参考本文档步骤2
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint(endpointName, regionId, product, domain);
        } catch (ClientException e) {
            log.debug("Initialize ro with exception {}",e.getMessage());
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        return acsClient;
    }

}
