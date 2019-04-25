package com.minlia.module.wechat.ma.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.wechat.ma.bean.entity.WechatMaUser;
import org.springframework.context.ApplicationEvent;

/**
 * Created by garen on 2017/10/11.
 */
public class WechatMaUpdatedEvent extends ApplicationEvent {

    public WechatMaUpdatedEvent(Object source) {
        super(source);
    }

    public static void onUpdated(WechatMaUser wechatMaUser){
        ContextHolder.getContext().publishEvent(new WechatMaUpdatedEvent(wechatMaUser));
    }

}
