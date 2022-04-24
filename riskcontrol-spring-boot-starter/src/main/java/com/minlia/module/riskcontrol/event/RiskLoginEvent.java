package com.minlia.module.riskcontrol.event;

import lombok.Setter;

@Setter
public class RiskLoginEvent extends Event {

    public RiskLoginEvent(String username) {
        super();
        this.setScene("login_failure");
        this.setSceneValue(username);
    }

}