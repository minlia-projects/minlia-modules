package com.minlia.module.pooul.event;

import org.springframework.context.ApplicationEvent;

/**
 * 支付失败事件
 * Created by garen on 2018/7/24.
 */
public class PooulPayFailureEvent extends ApplicationEvent{

    public PooulPayFailureEvent(Object source) {
        super(source);
    }

}
