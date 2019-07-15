package com.minlia.module.riskcontrol.event;

import lombok.Data;

@Data
public class RiskBlackIpEvent extends Event {

    public RiskBlackIpEvent(String sceneValue) {
        super();
        setScene("BLACK_IP");
        setSceneValue(sceneValue);
    }

}
