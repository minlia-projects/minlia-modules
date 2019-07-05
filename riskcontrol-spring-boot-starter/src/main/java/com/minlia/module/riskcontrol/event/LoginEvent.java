package com.minlia.module.riskcontrol.event;

import lombok.Setter;

@Setter
public class LoginEvent extends Event {

    public final static String USERNAME = "username";

    public final static String IP = "ip";

    public final static String PASSWORD = "password";

    private String username;

    private String password;

    private String ip;

    public String getUsername() {
        return username;
    }

    public String getIp() {
        return ip;
    }

    public String getPassword() {
        return System.currentTimeMillis() + "";
    }

}