package com.minlia.module.riskcontrol.config;

import com.minlia.module.common.annotation.ConfigAutowired;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author garen
 * @version 1.0
 * @description 风控配置
 * @date 2019/8/22 3:05 PM
 */
@Data
@Component
@ConfigAutowired(type = "SYS_RISK_CONFIG")
public class RiskcontrolConfig {

    private Boolean realSwitchFlag = true;

}