package com.minlia.module.member.event;

import com.minlia.cloud.holder.ContextHolder;
import org.springframework.context.ApplicationEvent;

/**
 * @author garen
 * @date 2017/7/13
 * 用户认证成功事件
 */
public class SysUserAuthEvent extends ApplicationEvent {

    public SysUserAuthEvent(Object source) {
        super(source);
    }

    public static void onAuthed(Long uid) {
        ContextHolder.getContext().publishEvent(new SysUserAuthEvent(uid));
    }

}