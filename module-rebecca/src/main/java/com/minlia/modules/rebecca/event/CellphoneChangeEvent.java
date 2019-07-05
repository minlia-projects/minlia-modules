package com.minlia.modules.rebecca.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.modules.rebecca.bean.domain.User;
import org.springframework.context.ApplicationEvent;

/**
 * Created by garen on 2017/7/13.
 * 手机号码变更事件
 */
public class CellphoneChangeEvent extends ApplicationEvent {

    public CellphoneChangeEvent(User source) {
        super(source);
    }

    public static void onChange(User user) {
        ContextHolder.getContext().publishEvent(new CellphoneChangeEvent(user));
    }

}

