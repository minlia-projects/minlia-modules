package com.minlia.module.sms.config;

import com.minlia.module.common.annotation.ConfigAutowired;
import com.minlia.module.sms.enums.SmsChannelEnum;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author garen
 * @version 1.0
 * @description 短信配置
 * @date 2019/8/22 3:05 PM
 */
@Data
@Component
@ConfigAutowired(type = "SYS_SMS_CONFIG")
public class SmsConfig {

    private Boolean realSwitchFlag = true;

    private SmsChannelEnum channel = SmsChannelEnum.ALIYUN;

}