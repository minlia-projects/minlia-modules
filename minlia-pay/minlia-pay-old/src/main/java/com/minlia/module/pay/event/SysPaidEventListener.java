package com.minlia.module.pay.event;

import com.minlia.module.pay.bean.SysPaidResult;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SysPaidEventListener {

    @EventListener
    public void onPaid(SysPaidEvent event) {
        SysPaidResult result = (SysPaidResult) event.getSource();
        System.out.println(result.getStatus());
    }

}
