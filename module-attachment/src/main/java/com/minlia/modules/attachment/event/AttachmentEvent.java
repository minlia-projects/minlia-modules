package com.minlia.modules.attachment.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.modules.attachment.entity.Attachment;
import org.springframework.context.ApplicationEvent;

/**
 * Created by garen on 2018/4/15.
 */
public class AttachmentEvent extends ApplicationEvent {

    public AttachmentEvent(Object source) {
        super(source);
    }

    public static void onUpload(Attachment attachment) {
        ContextHolder.getContext().publishEvent(new AttachmentEvent(attachment));
    }

}
