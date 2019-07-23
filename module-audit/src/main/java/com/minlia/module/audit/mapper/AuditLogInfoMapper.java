package com.minlia.module.audit.mapper;

import com.minlia.module.audit.entity.AuditLogInfo;
import com.minlia.module.audit.bean.AuditLogInfoQRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuditLogInfoMapper {

    int insertSelective(@Param("auditLogInfo") AuditLogInfo auditLogInfo);

    int updateByPrimaryKeySelective(@Param("auditLogInfo") AuditLogInfo auditLogInfo);

    List<AuditLogInfo> selectByAll(AuditLogInfoQRO qro);

}
