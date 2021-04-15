package com.minlia.hsjs.integral.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.hsjs.integral.bean.HsjsIntegralMinusData;
import com.minlia.hsjs.integral.bean.HsjsIntegralPlusData;
import org.springframework.context.ApplicationEvent;

/**
 * @author garen
 */
public class HsjsIntegralMinusEvent extends ApplicationEvent {

    public HsjsIntegralMinusEvent(HsjsIntegralMinusData source) {
        super(source);
    }

    public static void publish(HsjsIntegralMinusData data) {
        ContextHolder.getContext().publishEvent(new HsjsIntegralMinusEvent(data));
    }

}