package com.minlia.module.rebecca.user.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import org.springframework.context.ApplicationEvent;

/**
 * @author garen
 * @date 2017/7/13
 * 系统用户注册相关事件
 */
public class SysUserCreateEvent extends ApplicationEvent {

    public SysUserCreateEvent(Object source) {
        super(source);
    }

    /**
     * 当完成注册时的事件
     * 传入后续事件处理的入参
     * 其它业务由业务系统完成
     * 如绑定其它账户
     *
     * @param entity
     */
    public static void onCompleted(SysUserEntity entity) {
        ContextHolder.getContext().publishEvent(new SysUserCreateEvent(entity));
    }

}

