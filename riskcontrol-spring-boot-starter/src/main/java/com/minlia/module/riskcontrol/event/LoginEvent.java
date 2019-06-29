package com.minlia.module.riskcontrol.event;

import lombok.Data;

/**
 * Created by sunpeak on 2016/8/6.
 */
@Data
public class LoginEvent extends Event {

    public final static String USERNAME = "username";

    public final static String MOBILE = "mobile";

    public final static String EMAIL = "email";

    public final static String IP = "ip";

    private String mobile;

    private String operateIp;

}