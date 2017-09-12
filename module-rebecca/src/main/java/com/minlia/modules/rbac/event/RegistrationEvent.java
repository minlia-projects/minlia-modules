package com.minlia.modules.rbac.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by garen on 2017/7/13.
 * 系统用户注册相关事件
 */
public class RegistrationEvent<User> extends ApplicationEvent{

    public RegistrationEvent(Object source) {
        super(source);
    }

}
