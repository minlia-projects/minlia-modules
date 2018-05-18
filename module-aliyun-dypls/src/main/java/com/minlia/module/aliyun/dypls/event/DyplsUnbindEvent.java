package com.minlia.module.aliyun.dypls.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.aliyun.dypls.entity.DyplsBind;
import org.springframework.context.ApplicationEvent;

/**
 * Created by garen on 2018/05/18.
 */
public class DyplsUnbindEvent extends ApplicationEvent {

    public DyplsUnbindEvent(Object source) {
        super(source);
    }

    public static void unbind(String secretNo) {
        ContextHolder.getContext().publishEvent(new DyplsUnbindEvent(secretNo));
    }

}
