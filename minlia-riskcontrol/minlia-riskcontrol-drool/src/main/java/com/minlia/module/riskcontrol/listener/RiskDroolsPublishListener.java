//package com.minlia.module.riskcontrol.listener;
//
//import com.minlia.module.riskcontrol.service.RiskDroolsConfigService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RiskDroolsPublishListener implements MessageListener {
//
//    @Autowired
//    private RiskDroolsConfigService riskDroolsConfigService;
//
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        riskDroolsConfigService.reset();
//    }
//
//}