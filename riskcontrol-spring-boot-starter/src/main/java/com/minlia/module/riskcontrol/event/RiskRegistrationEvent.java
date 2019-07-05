package com.minlia.module.riskcontrol.event;

import lombok.Data;

@Data
public class RiskRegistrationEvent extends Event {

    public final static String USERNAME = "username";

    public final static String IP = "ip";

    public final static String TIME = "time";

    private String username;

    private String ip;

    private long time;

}