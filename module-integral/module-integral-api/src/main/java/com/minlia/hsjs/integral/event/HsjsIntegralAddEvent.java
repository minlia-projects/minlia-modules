package com.minlia.hsjs.integral.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.hsjs.integral.bean.HsjsIntegralAddData;
import org.springframework.context.ApplicationEvent;

/**
 * @author garen
 */
public class HsjsIntegralAddEvent extends ApplicationEvent {

    public HsjsIntegralAddEvent(HsjsIntegralAddData source) {
        super(source);
    }

    public static void publish(HsjsIntegralAddData data) {
        ContextHolder.getContext().publishEvent(new HsjsIntegralAddEvent(data));
    }

}