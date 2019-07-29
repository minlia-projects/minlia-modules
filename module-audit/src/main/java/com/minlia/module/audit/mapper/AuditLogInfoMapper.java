package com.minlia.module.audit.mapper;

import com.minlia.module.audit.bean.AuditLogInfoQRO;
import com.minlia.module.audit.entity.AuditLogInfo;

import java.util.List;

public interface AuditLogInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(AuditLogInfo record);

    AuditLogInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuditLogInfo record);

    List<AuditLogInfo> selectByAll(AuditLogInfoQRO qro);
}