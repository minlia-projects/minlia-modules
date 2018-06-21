package com.minlia.modules.rbac.openapi.registration.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.modules.rbac.backend.user.entity.User;
import org.springframework.context.ApplicationEvent;

/**
 * Created by garen on 2017/7/13.
 * 系统用户注册相关事件
 */
public class RegistrationEvent extends ApplicationEvent {

    public RegistrationEvent(Object source) {
        super(source);
    }

    /**
     * 当完成注册时的事件
     * 传入后续事件处理的入参
     * 其它业务由业务系统完成
     * 如绑定其它账户
     * @param userId
     */
    public static void onCompleted(Long userId) {
        ContextHolder.getContext().publishEvent(new RegistrationEvent(userId));
    }

}

