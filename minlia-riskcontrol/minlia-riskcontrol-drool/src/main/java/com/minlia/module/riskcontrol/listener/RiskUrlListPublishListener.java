//package com.minlia.module.riskcontrol.listener;
//
//import com.minlia.module.riskcontrol.service.RiskBlackUrlService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RiskUrlListPublishListener implements MessageListener {
//
//    @Autowired
//    private RiskBlackUrlService riskBlackUrlService;
//
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        riskBlackUrlService.updateCache();
//    }
//
//}