package com.minlia.module.wechat.ma.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.wechat.ma.entity.WechatMaUser;
import org.springframework.context.ApplicationEvent;

/**
 * Created by garen on 2017/10/11.
 */
public class WechatDetailUpdatedEvent extends ApplicationEvent {

    public WechatDetailUpdatedEvent(Object source) {
        super(source);
    }

    public static void onUpdated(WechatMaUser wechatMaUser){
        ContextHolder.getContext().publishEvent(new WechatDetailUpdatedEvent(wechatMaUser));
    }

}
