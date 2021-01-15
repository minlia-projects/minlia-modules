package com.minlia.module.currency.config;

import com.minlia.module.common.annotation.ConfigAutowired;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author garen
 * @version 1.0
 * @description 汇率配置
 * @date 2019/8/22 3:05 PM
 */
@Data
@Component
@ConfigAutowired(type = "SYS_CURRENCY_RATE")
public class CurrencyRateConfig {

    private String appKey;

    private String sign;

}