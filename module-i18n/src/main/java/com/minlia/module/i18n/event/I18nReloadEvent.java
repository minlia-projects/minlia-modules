package com.minlia.module.i18n.event;

import com.minlia.cloud.holder.ContextHolder;
import org.springframework.context.ApplicationEvent;

/**
 * Created by garen on 2017/7/13.
 */
public class I18nReloadEvent extends ApplicationEvent {

    public I18nReloadEvent() {
        super(true);
    }

    public static void onReload() {
        if (null != ContextHolder.getContext()) {
            ContextHolder.getContext().publishEvent(new I18nReloadEvent());
        }
    }

}