package com.minlia.module.realname.event;

import com.minlia.cloud.holder.ContextHolder;
import org.springframework.context.ApplicationEvent;

/**
 * 实名风控触发事件
 *
 * @author garen
 */
public class RiskRealNameTriggerEvent extends ApplicationEvent {

    public RiskRealNameTriggerEvent(Long uid) {
        super(uid);
    }

    public static void publish(Long uid) {
        ContextHolder.getContext().publishEvent(new RiskRealNameTriggerEvent(uid));
    }

}