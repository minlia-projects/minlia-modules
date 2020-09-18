package com.minlia.module.unified.payment.util;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.unified.payment.CreatePreOrderService;
import com.minlia.module.unified.payment.enumeration.PayChannelEnum;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/4/15 4:27 PM
 */
public class UnifiedPayClient {

    public static Response create(PayChannelEnum channel, Object t) {
        CreatePreOrderService service = ContextHolder.getContext().getBean(channel.name() + "CreatePreOrderService", CreatePreOrderService.class);
        if (null == service) {
            throw new RuntimeException("Unsupported gateway");
        }
        return service.createOrder(t);
    }

}