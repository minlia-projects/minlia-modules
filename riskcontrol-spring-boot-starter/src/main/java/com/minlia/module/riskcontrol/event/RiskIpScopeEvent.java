package com.minlia.module.riskcontrol.event;

import lombok.Data;

@Data
public class RiskIpScopeEvent extends Event {

    public RiskIpScopeEvent() {
        super();
        setScene("APP_OUT_SET_IP");
        setSceneValue(getIp());
    }

}