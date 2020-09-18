package com.minlia.module.audit.service.impl;

import com.minlia.module.audit.entity.AuditLogEntity;
import com.minlia.module.audit.mapper.AuditLogMapper;
import com.minlia.module.audit.service.AuditLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 审计日志 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-08-21
 */
@Service
public class AuditLogServiceImpl extends ServiceImpl<AuditLogMapper, AuditLogEntity> implements AuditLogService {

}
