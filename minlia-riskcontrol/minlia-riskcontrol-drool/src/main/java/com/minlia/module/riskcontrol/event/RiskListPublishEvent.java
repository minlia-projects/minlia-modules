package com.minlia.module.riskcontrol.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.riskcontrol.entity.RiskList;
import org.springframework.context.ApplicationEvent;

/**
 * 风控发布事件
 *
 * @author garen
 */
public class RiskListPublishEvent extends ApplicationEvent {

    public RiskListPublishEvent(RiskList riskList) {
        super(riskList);
    }

    public static void execute(RiskList riskList) {
        ContextHolder.getContext().publishEvent(new RiskListPublishEvent(riskList));
    }

}