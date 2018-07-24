package com.minlia.module.pooul.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.pooul.body.pay.PooulPayNotifyResponseBody;

/**
 * 事件发布器
 * Created by garen on 2018/7/24.
 */
public class PooulEventPublisher {

    public static void onPaySuccess(PooulPayNotifyResponseBody responseBody) {
        ContextHolder.getContext().publishEvent(new PooulPaySuccessEvent(responseBody));
    }

    public static void onPayFailure(PooulPayNotifyResponseBody responseBody) {
        ContextHolder.getContext().publishEvent(new PooulPayFailureEvent(responseBody));
    }

}
