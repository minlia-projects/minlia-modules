package com.minlia.hsjs.integral.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.hsjs.integral.bean.HsjsIntegralSendData;
import org.springframework.context.ApplicationEvent;

/**
 * @author garen
 */
public class HsjsIntegralSendEvent extends ApplicationEvent {

    public HsjsIntegralSendEvent(HsjsIntegralSendData source) {
        super(source);
    }

    public static void publish(HsjsIntegralSendData data) {
        ContextHolder.getContext().publishEvent(new HsjsIntegralSendEvent(data));
    }

}