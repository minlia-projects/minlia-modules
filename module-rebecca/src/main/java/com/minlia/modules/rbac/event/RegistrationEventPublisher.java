package com.minlia.modules.rbac.event;


import com.minlia.cloud.holder.ContextHolder;
import com.minlia.modules.rbac.domain.User;

/**
 * 系统用户注册事件发布器, 在注册相关事件完成后发布
 */
public class RegistrationEventPublisher {

    /**
     * 当完成注册时的事件
     * 传入后续事件处理的入参
     * 其它业务由业务系统完成
     * 如绑定其它账户
     * @param user
     */
    public static void onCompleted(User user) {
        ContextHolder.getContext().publishEvent(new RegistrationEvent(user));
    }

}
