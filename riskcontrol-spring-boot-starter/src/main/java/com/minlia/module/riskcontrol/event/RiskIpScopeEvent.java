package com.minlia.module.riskcontrol.event;

import lombok.Data;

@Data
public class RiskIpScopeEvent extends Event {

    public RiskIpScopeEvent() {
        super();
        setScene("IP_SCOPE");
    }

}
