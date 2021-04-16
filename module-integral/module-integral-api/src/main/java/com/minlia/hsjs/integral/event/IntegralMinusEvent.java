package com.minlia.hsjs.integral.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.hsjs.integral.bean.IntegralMinusData;
import org.springframework.context.ApplicationEvent;

/**
 * @author garen
 */
public class IntegralMinusEvent extends ApplicationEvent {

    public IntegralMinusEvent(IntegralMinusData source) {
        super(source);
    }

    public static void publish(IntegralMinusData data) {
        ContextHolder.getContext().publishEvent(new IntegralMinusEvent(data));
    }

}