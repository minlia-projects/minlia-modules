package com.minlia.module.rebecca.risk.event;

import com.minlia.module.riskcontrol.event.Event;
import lombok.Setter;

/**
 * 登陆失败事件
 */
@Setter
public class RiskLoginFailureEvent extends Event {

    public RiskLoginFailureEvent(String username) {
        super();
        this.setScene("MAX_NUM_ACCESS_15MINS");
        this.setSceneValue(username);
    }

}