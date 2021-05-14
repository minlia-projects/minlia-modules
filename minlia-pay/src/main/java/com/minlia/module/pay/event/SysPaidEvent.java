package com.minlia.module.pay.event;


import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.pay.bean.SysPaidResult;
import org.springframework.context.ApplicationEvent;

/**
 * 需要在接收到此事件后进行业务逻辑判断
 * 如: 是否需要进行状态更新等操作
 */
public class SysPaidEvent extends ApplicationEvent {

    public SysPaidEvent(SysPaidResult source) {
        super(source);
    }

    public static void onPaid(SysPaidResult result) {
        ContextHolder.getContext().publishEvent(new SysPaidEvent(result));
    }

}