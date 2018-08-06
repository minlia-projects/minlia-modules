//package com.minlia.module.wallet.v1.listener;
//
//import com.minlia.module.security.v1.user.registration.RegistrationEvent;
//import com.minlia.module.wallet.v1.service.WalletService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.event.EventListener;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
//@Component
//public class WalletEventReceivers {
//
//    @Autowired
//    private WalletService walletService;
//
//    @Async
//    @EventListener
//    public void onComplete(RegistrationEvent<Long> event) {
//        //注册完成初始化钱包
//        Long id = (Long) event.getSource();
//        walletService.init(id);
//    }
//
//}
