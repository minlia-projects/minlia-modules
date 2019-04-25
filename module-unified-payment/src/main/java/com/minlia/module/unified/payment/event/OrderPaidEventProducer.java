package com.minlia.module.unified.payment.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.unified.payment.bean.OrderPaidNotificationResponse;

public class OrderPaidEventProducer {

    public static void onOrderPaid(OrderPaidNotificationResponse body) {
        ContextHolder.getContext().publishEvent(new OrderPaidEvent(body));
    }

}
