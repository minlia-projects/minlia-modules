package com.minlia.module.audit.mapper;

import com.minlia.module.audit.bean.AuditLogInfoQRO;
import com.minlia.module.audit.entity.AuditLogInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuditLogInfoMapper {

    int insertSelective(AuditLogInfo auditLogInfo);

    int updateByPrimaryKeySelective(AuditLogInfo auditLogInfo);

    int deleteByPrimaryKey(Long id);

    AuditLogInfo selectByPrimaryKey(Long id);

    List<AuditLogInfo> selectByAll(AuditLogInfoQRO qro);

}
