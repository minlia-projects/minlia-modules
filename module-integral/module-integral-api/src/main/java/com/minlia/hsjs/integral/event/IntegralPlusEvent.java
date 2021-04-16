package com.minlia.hsjs.integral.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.hsjs.integral.bean.IntegralPlusData;
import org.springframework.context.ApplicationEvent;

/**
 * @author garen
 */
public class IntegralPlusEvent extends ApplicationEvent {

    public IntegralPlusEvent(IntegralPlusData source) {
        super(source);
    }

    public static void publish(IntegralPlusData data) {
        ContextHolder.getContext().publishEvent(new IntegralPlusEvent(data));
    }

}