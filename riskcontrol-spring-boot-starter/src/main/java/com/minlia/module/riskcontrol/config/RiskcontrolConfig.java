package com.minlia.module.riskcontrol.config;

import com.minlia.module.bible.annotation.BibleAutowired;
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
@BibleAutowired(type = "RISK_CONFIG")
public class RiskcontrolConfig {

    private boolean realSwitchFlag;

}