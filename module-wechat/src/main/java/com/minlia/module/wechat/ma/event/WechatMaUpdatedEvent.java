package com.minlia.module.wechat.ma.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by garen on 2017/10/11.
 */
public class WechatMaUpdatedEvent extends ApplicationEvent {

    public WechatMaUpdatedEvent(Object source) {
        super(source);
    }


}
