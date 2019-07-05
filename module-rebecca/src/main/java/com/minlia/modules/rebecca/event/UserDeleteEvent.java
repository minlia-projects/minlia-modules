package com.minlia.modules.rebecca.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.modules.rebecca.bean.domain.User;
import org.springframework.context.ApplicationEvent;

/**
 * 用户删除事件
 * Created by garen on 2017/7/13.
 */
public class UserDeleteEvent extends ApplicationEvent {

    public UserDeleteEvent(User source) {
        super(source);
    }

    public static void onDelete(User user) {
        ContextHolder.getContext().publishEvent(new UserDeleteEvent(user));
    }

}

