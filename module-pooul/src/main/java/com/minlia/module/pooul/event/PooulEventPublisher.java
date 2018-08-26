package com.minlia.module.pooul.event;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.pooul.bean.dto.PooulPayNotifyDTO;

/**
 * 事件发布器
 * Created by garen on 2018/7/24.
 */
public class PooulEventPublisher {

    public static void onPaid(PooulPayNotifyDTO responseBody) {
        ContextHolder.getContext().publishEvent(new PooulPaidNotifyEvent(responseBody));
    }

    public static void onPaySuccess(PooulPayNotifyDTO responseBody) {
        ContextHolder.getContext().publishEvent(new PooulPaySuccessEvent(responseBody));
    }

    public static void onPayFailure(PooulPayNotifyDTO responseBody) {
        ContextHolder.getContext().publishEvent(new PooulPayFailureEvent(responseBody));
    }

}
