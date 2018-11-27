package com.minlia.modules.aliyun.sms.event;
//package com.minlia.modules.starter.sms;
//
//import com.minlia.boot.context.ContextHolder;
//import com.minlia.boot.env.EnvironmentUtils;
//import com.minlia.modules.starter.sms.bean.NotificationRequestBody;
//import com.minlia.modules.starter.sms.event.NotificationEvent;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.event.TransactionalEventListener;
//
///**
// * Created by will on 7/10/17.
// */
//@Component
//public class SmsNotificationReceiver {
//
//
//    @TransactionalEventListener
//    @EventListener
//    public void handleNotificationEvent(NotificationEvent<NotificationRequestBody> event) {
//        NotificationRequestBody bean = (NotificationRequestBody) event.getSource();
//
//        Boolean send=Boolean.FALSE;
//        if(!StringUtils.isEmpty(bean.getTo()) && EnvironmentUtils.isProduction()){
//            send=Boolean.TRUE;
//        }
//
//        if (send) {
//            ContextHolder.getContext().getBean(AliyunSmsSendService.class).send(bean.getBibleCode(),bean.getBibleItemCode(),bean.getTo(), bean.getJsonArguments());
//        } else {
//            ContextHolder.getContext().getBean(ConsoleSimulationSmsSendService.class).send(bean.getBibleCode(),bean.getBibleItemCode(),bean.getTo(), bean.getJsonArguments());
//        }
//
//    }
//
//
//}
