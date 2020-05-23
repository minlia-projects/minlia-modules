package com.minlia.module.audit.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.audit.entity.AuditLogInfo;
import com.minlia.module.audit.bean.AuditLogInfoQRO;

public interface AuditLogInfoService {

    int insertSelective(AuditLogInfo auditLogInfo);

    int updateByPrimaryKeySelective(AuditLogInfo auditLogInfo);

    PageInfo<AuditLogInfo> queryPage(AuditLogInfoQRO qro);

}
