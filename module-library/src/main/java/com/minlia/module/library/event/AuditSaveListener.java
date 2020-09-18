//package com.minlia.module.library.event;
//
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
///**
// * @author garen
// * @date 2017/8/14
// */
//@Component
//public class AuditSaveListener {
//
//    private final AuditLogService auditLogService;
//
//    public AuditSaveListener(AuditLogService auditLogService) {
//        this.auditLogService = auditLogService;
//    }
//
//    @EventListener
//    public void onSave(AuditSaveEvent event) {
//        AuditLogEntity entity = (AuditLogEntity) event.getSource();
//        auditLogService.save(entity);
//    }
//
//}
//
