package com.minlia.module.unified.payment.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.unified.payment.body.OrderPaidNotificationBody;

public class OrderPaidEventProducer {

    public static void onOrderPaid(OrderPaidNotificationBody body) {
        ContextHolder.getContext().publishEvent(new OrderPaidEvent(body));
    }

}
