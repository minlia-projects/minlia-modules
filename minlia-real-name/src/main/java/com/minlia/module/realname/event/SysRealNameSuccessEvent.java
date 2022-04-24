package com.minlia.module.realname.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.realname.bean.SysRealName2Dto;
import org.springframework.context.ApplicationEvent;

/**
 * 实名认证成功事件
 *
 * @author garen
 */
public class SysRealNameSuccessEvent extends ApplicationEvent {

    public SysRealNameSuccessEvent(SysRealName2Dto source) {
        super(source);
    }

    public static void publish(SysRealName2Dto dto) {
        ContextHolder.getContext().publishEvent(new SysRealNameSuccessEvent(dto));
    }

}