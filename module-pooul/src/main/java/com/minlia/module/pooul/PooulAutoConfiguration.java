package com.minlia.module.pooul;

//import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
//@EnableConfigurationProperties(value = {PooulPayProperties.class, PooulMerchantProperties.class})
public class PooulAutoConfiguration implements EnvironmentAware {

//    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
//        this.propertyResolver = new RelaxedPropertyResolver(environment, "pooul.");
    }

//    @Autowired
//    private AliyunSmsProperties value;
//
//    @Bean
//    public IAcsClient client(){
//        //初始化ascClient需要的几个参数
//        final String regionId = value.getRegionId();
//        final String endpointName = value.getEndpoint();
//        final String product = value.getProduct();//短信API产品名称（短信产品名固定，无需修改）
//        final String entity = value.getDomain();//短信API产品域名（接口地址固定，无需修改）
//        final String accessKeyId = value.getAccessId();//你的accessKeyId,参考本文档步骤2
//        final String accessKeySecret = value.getAccessKey();//你的accessKeySecret，参考本文档步骤2
//        //初始化ascClient,暂时不支持多region（请勿修改）
//        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
//        try {
//            DefaultProfile.addEndpoint(endpointName, regionId, product, entity);
//        } catch (ClientException e) {
//            log.debug("Initialize ro with exception {}",e.getMessage());
//        }
//        IAcsClient acsClient = new DefaultAcsClient(profile);
//        return acsClient;
//    }

}
