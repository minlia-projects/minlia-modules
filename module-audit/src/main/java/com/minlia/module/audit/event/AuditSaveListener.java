package com.minlia.module.audit.event;

import com.minlia.module.audit.entity.AuditLogEntity;
import com.minlia.module.audit.service.AuditLogService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author garen
 * @date 2017/8/14
 */
@Component
public class AuditSaveListener {

    private final AuditLogService auditLogService;

    public AuditSaveListener(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @Async
    @EventListener
    public void onSave(AuditSaveEvent event) {
        AuditLogEntity entity = (AuditLogEntity) event.getSource();
        auditLogService.save(entity);
    }

}

