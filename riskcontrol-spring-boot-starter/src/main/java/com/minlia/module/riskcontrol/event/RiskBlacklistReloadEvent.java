package com.minlia.module.riskcontrol.event;

import com.minlia.cloud.holder.ContextHolder;
import org.springframework.context.ApplicationEvent;

/**
 * Created by garen on 2017/7/13.
 */
public class RiskBlacklistReloadEvent extends ApplicationEvent {

    public RiskBlacklistReloadEvent() {
        super(true);
    }

    public static void onReload() {
        if (null != ContextHolder.getContext()) {
            ContextHolder.getContext().publishEvent(new RiskBlacklistReloadEvent());
        }
    }

}

