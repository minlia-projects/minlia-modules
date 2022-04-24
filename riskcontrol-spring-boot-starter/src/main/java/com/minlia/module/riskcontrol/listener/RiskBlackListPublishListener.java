package com.minlia.module.riskcontrol.listener;

import com.minlia.module.riskcontrol.service.RiskBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RiskBlackListPublishListener implements MessageListener {

    @Autowired
    private RiskBlackListService riskBlackListService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        riskBlackListService.updateCache();
    }

}