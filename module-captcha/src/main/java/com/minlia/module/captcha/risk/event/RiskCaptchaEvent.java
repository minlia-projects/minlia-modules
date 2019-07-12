package com.minlia.module.captcha.risk.event;

import com.minlia.module.riskcontrol.event.Event;
import lombok.Setter;

@Setter
public class RiskCaptchaEvent extends Event {

    public RiskCaptchaEvent() {
        super();
        this.setScene("NUM_OTP_30MINS");
    }

    public final static String ACCOUNT = "account";

    public final static String TIME = "time";

    private String account;

    private String time;

    public String getAccount() {
        return account;
    }

    public String getTime() {
        return System.currentTimeMillis() + "";
    }

}