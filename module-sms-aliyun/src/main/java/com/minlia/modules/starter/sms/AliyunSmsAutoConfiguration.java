package com.minlia.modules.starter.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.minlia.modules.starter.sms.properties.AliyunSmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Slf4j
@Configuration
@EnableConfigurationProperties(value = {AliyunSmsProperties.class })
@ConditionalOnClass(SendSmsRequest.class)
public class AliyunSmsAutoConfiguration implements EnvironmentAware{

    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, "aliyun.sms.");
    }
//    @Bean(value = "aliyunSmsSendService")
//    public SmsSendService aliyunSmsSendService() {
//        AliyunSmsSendService aliyunSmsSendService = new AliyunSmsSendService();
//        return aliyunSmsSendService;
//    }
//
//    @Bean(value = "consoleSimulationSmsSendService")
//    public SmsSendService consoleSimulationSmsSendService() {
//        ConsoleSimulationSmsSendService consoleSimulationSmsSendService = new ConsoleSimulationSmsSendService();
//        return consoleSimulationSmsSendService;
//    }

    @Autowired
    AliyunSmsProperties value;

    @Bean
    public IAcsClient client(){
        String regionId=value.getRegionId();
        String accessId=value.getAccessId();
        String accessKeySecretId=value.getAccessKey();
        String endpointName=value.getEndpoint();
//        String signName=value.getSignName();
        String product=value.getProduct();
        String domain=value.getDomain();

        IClientProfile profile = DefaultProfile.getProfile(regionId, accessId, accessKeySecretId);
        try {
            DefaultProfile.addEndpoint(endpointName, regionId, product, domain);
        } catch (ClientException e) {
            log.debug("Initialize bean with exception {}",e.getMessage());
        }
        IAcsClient client = new DefaultAcsClient(profile);

        return client;
    }

}
