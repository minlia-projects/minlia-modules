package com.minlia.module.rebecca.user.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import org.springframework.context.ApplicationEvent;

/**
 * @author garen
 * @date 2017/7/13
 * 手机号码变更事件
 */
public class SysEmailChangeEvent extends ApplicationEvent {

    public SysEmailChangeEvent(SysUserEntity source) {
        super(source);
    }

    public static void onChange(SysUserEntity entity) {
        ContextHolder.getContext().publishEvent(new SysEmailChangeEvent(entity));
    }

}

