//package com.minlia.module.riskcontrol.event;
//
//import lombok.Setter;
//
///**
// * 登陆失败事件
// */
//@Setter
//public class RiskLoginFailureEvent extends Event {
//
//    public RiskLoginFailureEvent() {
//        super();
//        this.setScene("login_failure");
//    }
//
//    public final static String ACCOUNT = "account";
//
//    public final static String TIME = "time";
//
//    private String account;
//
//    private String time;
//
//    public String getAccount() {
//        return account;
//    }
//
//    public String getTime() {
//        return System.currentTimeMillis() + "";
//    }
//
//}