package com.minlia.hsjs.integral.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.hsjs.integral.bean.HsjsIntegralPlusData;
import org.springframework.context.ApplicationEvent;

/**
 * @author garen
 */
public class HsjsIntegralPlusEvent extends ApplicationEvent {

    public HsjsIntegralPlusEvent(HsjsIntegralPlusData source) {
        super(source);
    }

    public static void publish(HsjsIntegralPlusData data) {
        ContextHolder.getContext().publishEvent(new HsjsIntegralPlusEvent(data));
    }

}