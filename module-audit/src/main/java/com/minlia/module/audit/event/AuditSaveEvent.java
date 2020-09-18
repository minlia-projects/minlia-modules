package com.minlia.module.audit.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.audit.entity.AuditLogEntity;
import org.springframework.context.ApplicationEvent;

/**
 * @author garen
 * @date 2017/7/13
 * 系统审计日志事件
 */
public class AuditSaveEvent extends ApplicationEvent {

    public AuditSaveEvent(Object source) {
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
    public static void publish(AuditLogEntity entity) {
        ContextHolder.getContext().publishEvent(new AuditSaveEvent(entity));
    }

}

