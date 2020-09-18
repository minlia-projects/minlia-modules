package com.minlia.module.rebecca.user.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import org.springframework.context.ApplicationEvent;

/**
 * @author garen
 * @date 2017/7/13
 * 登录成功事件
 */
public class SysLoginSuccessEvent extends ApplicationEvent {

    public SysLoginSuccessEvent(SysUserEntity source) {
        super(source);
    }

    public static void publish(SysUserEntity entity) {
        ContextHolder.getContext().publishEvent(new SysLoginSuccessEvent(entity));
    }

}