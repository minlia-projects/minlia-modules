package com.minlia.modules.security.risk.event;

import com.minlia.module.riskcontrol.event.Event;
import lombok.Data;

@Data
public class RiskRegistrationEvent extends Event {

    public final static String USERNAME = "username";

    public final static String TIME = "time";

    private String username;

    private long time;

}