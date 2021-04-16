package com.minlia.hsjs.integral.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.hsjs.integral.bean.IntegralSendData;
import org.springframework.context.ApplicationEvent;

/**
 * @author garen
 */
public class IntegralSendEvent extends ApplicationEvent {

    public IntegralSendEvent(IntegralSendData source) {
        super(source);
    }

    public static void publish(IntegralSendData data) {
        ContextHolder.getContext().publishEvent(new IntegralSendEvent(data));
    }

}