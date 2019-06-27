package com.minlia.module.riskcontrol.event;

import lombok.Data;

/**
 * Created by sunpeak on 2016/8/6.
 */
@Data
public class LoginEvent extends Event {

    public final static String MOBILE = "mobile";

    public final static String OPERATEIP = "operateIp";

    private String mobile;

    private String operateIp;

}