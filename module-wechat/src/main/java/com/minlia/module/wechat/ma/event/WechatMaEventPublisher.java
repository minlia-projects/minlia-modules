package com.minlia.module.wechat.ma.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.wechat.login.entity.WechatUser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


/**
 * 微信小程序事件发布器
 */
@Data
@Slf4j
public class WechatMaEventPublisher {

    public static void onAuthorized(WechatUser wechatUser) {
        WechatMaAuthorizedEvent event = new WechatMaAuthorizedEvent(wechatUser);
        ContextHolder.getContext().publishEvent(event);
        log.debug("已发布事件: {}", event);
    }

    public static void onUpdated(WechatUser wechatUser) {
        WechatMaUpdatedEvent event = new WechatMaUpdatedEvent(wechatUser);
        ContextHolder.getContext().publishEvent(event);
        log.debug("已发布事件: {}", event);
    }

}
