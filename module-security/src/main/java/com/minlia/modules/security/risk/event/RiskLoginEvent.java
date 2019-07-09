package com.minlia.modules.security.risk.event;

import com.minlia.module.riskcontrol.event.Event;
import lombok.Setter;

@Setter
public class RiskLoginEvent extends Event {

    public RiskLoginEvent() {
        super();
        this.setScene("login_ip");
    }

    public final static String USERNAME = "username";

    public final static String TIME = "time";

    private String username;

    private String time;

    public String getUsername() {
        return username;
    }

    public String getTime() {
        return System.currentTimeMillis() + "";
    }

}