package com.minlia.module.rebecca.user.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import org.springframework.context.ApplicationEvent;

/**
 * 用户删除事件
 *
 * @author garen
 * @date 2017/7/13
 */
public class SysUserDeleteEvent extends ApplicationEvent {

    public SysUserDeleteEvent(SysUserEntity source) {
        super(source);
    }

    public static void onDelete(SysUserEntity entity) {
        ContextHolder.getContext().publishEvent(new SysUserDeleteEvent(entity));
    }

}