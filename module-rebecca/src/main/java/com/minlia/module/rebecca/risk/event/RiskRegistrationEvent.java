package com.minlia.module.rebecca.risk.event;

import com.minlia.module.riskcontrol.event.Event;
import lombok.Data;

/**
 * @author garen
 */
@Data
public class RiskRegistrationEvent extends Event {

    public RiskRegistrationEvent(String username) {
        super();
        this.setScene("SAME_IP_REGISTER");
        this.setSceneValue(username);
    }

}