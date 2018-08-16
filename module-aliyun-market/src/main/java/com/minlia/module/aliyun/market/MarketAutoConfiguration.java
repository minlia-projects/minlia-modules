package com.minlia.module.aliyun.market;

import com.minlia.module.aliyun.market.config.AliyunMarketProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by will on 6/21/17.
 */
@Configuration
@EnableConfigurationProperties(value = {AliyunMarketProperties.class })
public class MarketAutoConfiguration {

}
