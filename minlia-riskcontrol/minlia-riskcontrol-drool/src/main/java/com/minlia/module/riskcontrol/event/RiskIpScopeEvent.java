package com.minlia.module.riskcontrol.event;

import lombok.Data;

/**
 * 风控 IP范围
 *
 * @author garen
 */
@Data
public class RiskIpScopeEvent extends Event {

    public RiskIpScopeEvent() {
        super();
        setScene("APP_OUT_SET_IP");
        setSceneValue(getIp());
    }

}