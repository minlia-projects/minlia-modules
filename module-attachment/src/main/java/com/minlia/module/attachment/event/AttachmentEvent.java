package com.minlia.module.attachment.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.attachment.entity.SysAttachmentEntity;
import org.springframework.context.ApplicationEvent;

/**
 * @author garen
 * @date 2018/4/15
 */
public class AttachmentEvent extends ApplicationEvent {

    public AttachmentEvent(Object source) {
        super(source);
    }

    public static void publish(SysAttachmentEntity entity) {
        ContextHolder.getContext().publishEvent(new AttachmentEvent(entity));
    }

}