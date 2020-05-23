package com.minlia.modules.rebecca.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.modules.rebecca.bean.domain.User;
import org.springframework.context.ApplicationEvent;

/**
 * Created by garen on 2017/7/13.
 * 登录成功事件
 */
public class LoginSuccessEvent extends ApplicationEvent {

    public LoginSuccessEvent(User source) {
        super(source);
    }

    public static void onSuccess(User user) {
        ContextHolder.getContext().publishEvent(new LoginSuccessEvent(user));
    }

}

