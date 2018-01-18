package com.minlia.module.tencent.miniapp.event;

import com.minlia.cloud.holder.ContextHolder;
import org.springframework.context.ApplicationEvent;

/**
 * Created by garen on 2017/10/11.
 */
public class WechatDetailUpdatedEvent<WechatUserDetail> extends ApplicationEvent {

    public WechatDetailUpdatedEvent(Object source) {
        super(source);
    }

    public static void onUpdated(WechatDetailUpdatedEvent event){
        ContextHolder.getContext().publishEvent(event);
    }

}
