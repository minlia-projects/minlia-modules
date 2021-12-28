package com.minlia.module.realname.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.realname.bean.SysRealNameDTO;
import org.springframework.context.ApplicationEvent;

/**
 * @author garen
 */
public class SysRealNameAuthEvent extends ApplicationEvent {

    public SysRealNameAuthEvent(SysRealNameDTO source) {
        super(source);
    }

    public static void publish(SysRealNameDTO dto) {
        ContextHolder.getContext().publishEvent(new SysRealNameAuthEvent(dto));
    }

}