package com.minlia.module.unified.payment.event;


import com.minlia.module.unified.payment.body.OrderPaidNotificationBody;
import org.springframework.context.ApplicationEvent;

/**
 * 需要在接收到此事件后进行业务逻辑判断
 * 如: 是否需要进行状态更新等操作
 */
public class OrderPaidEvent extends ApplicationEvent {

    public OrderPaidEvent(OrderPaidNotificationBody source) {
        super(source);
    }
}
