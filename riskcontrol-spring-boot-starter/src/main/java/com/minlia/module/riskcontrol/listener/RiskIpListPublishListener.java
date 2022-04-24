package com.minlia.module.riskcontrol.listener;

import com.minlia.module.riskcontrol.service.RiskIpListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RiskIpListPublishListener implements MessageListener {

    @Autowired
    private RiskIpListService riskIpListService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        riskIpListService.updateCache();
    }

}