//package com.minlia.module.riskcontrol.listener;
//
//import com.minlia.module.riskcontrol.service.RiskListService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RiskBlackListPublishListener implements MessageListener {
//
//    @Autowired
//    private RiskListService riskListService;
//
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        riskListService.updateCache();
//    }
//
//}