package com.minlia.modules.aliyun.dypls;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.minlia.module.bible.service.BibleItemService;
import com.minlia.modules.aliyun.dypls.config.DyplsConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DyplsAutoConfiguration {

    //产品名称:云通信隐私保护产品,开发者无需替换
    static final String product = "Dyplsapi";
    //产品域名,开发者无需替换
    static final String domain = "dyplsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "yourAccessKeyId";
    static final String accessKeySecret = "yourAccessKeySecret";

    @Autowired
    private BibleItemService bibleItemService;

    @Bean
    public DyplsConfig config(){
        String poolKey = bibleItemService.get("ALIYUN","dypls_pool_key");
        DyplsConfig config = new DyplsConfig();
        config.setPoolKey(poolKey);
        return config;
    }

    @Bean
    public IAcsClient acsClient()  {
        String accessKeyId = bibleItemService.get("ALIYUN","access_key_id");
        String accessKeySecret = bibleItemService.get("ALIYUN","access_key_secret");

        if (StringUtils.isAnyEmpty(accessKeyId,accessKeySecret)) {
            return null;
        }

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        return  acsClient;
    }

}
