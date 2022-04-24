package com.minlia.module.riskcontrol.event;

import lombok.Data;

/**
 * 黑名单 事件
 *
 * @author garen
 */
@Data
public class RiskBlackIpEvent extends Event {

    public RiskBlackIpEvent() {
        super();
        setScene("BLACK_IP");
        setSceneValue(super.getIp());
    }

}