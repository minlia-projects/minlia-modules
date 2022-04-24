package com.minlia.module.rebecca.risk.event;

import com.minlia.module.riskcontrol.event.Event;
import lombok.Setter;

@Setter
public class RiskLoginEvent extends Event {

    public RiskLoginEvent(String username) {
        super();
        this.setScene("login_ip");
        this.setSceneValue(username);
    }

}