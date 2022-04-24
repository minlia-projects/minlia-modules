package com.minlia.module.captcha.risk.event;

import com.minlia.module.riskcontrol.event.Event;
import lombok.Data;

/**
 * 风控短信发送频率事件
 *
 * @author garen
 */
@Data
public class RiskCaptchaSmsEvent extends Event {

    public RiskCaptchaSmsEvent(String mobile) {
        super();
        this.setScene("SAME_ID_SMS_NUM");
        this.setSceneValue(mobile);
    }

}