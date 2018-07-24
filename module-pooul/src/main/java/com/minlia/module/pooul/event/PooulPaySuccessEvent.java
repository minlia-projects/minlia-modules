package com.minlia.module.pooul.event;

import org.springframework.context.ApplicationEvent;

/**
 * 支付成功事件
 * Created by garen on 2018/7/24.
 */
public class PooulPaySuccessEvent extends ApplicationEvent{

    public PooulPaySuccessEvent(Object source) {
        super(source);
    }

}
