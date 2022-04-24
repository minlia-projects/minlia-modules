package com.minlia.module.riskcontrol.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.riskcontrol.entity.RiskList;
import org.springframework.context.ApplicationEvent;

/**
 * 风控删除事件
 *
 * @author garen
 */
public class RiskListDeleteEvent extends ApplicationEvent {

    public RiskListDeleteEvent(RiskList riskList) {
        super(riskList);
    }

    public static void execute(RiskList riskList) {
        ContextHolder.getContext().publishEvent(new RiskListDeleteEvent(riskList));
    }

}