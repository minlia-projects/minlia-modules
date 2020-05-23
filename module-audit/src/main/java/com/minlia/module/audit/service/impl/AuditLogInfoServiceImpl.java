package com.minlia.module.audit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.module.audit.bean.AuditLogInfoQRO;
import com.minlia.module.audit.entity.AuditLogInfo;
import com.minlia.module.audit.mapper.AuditLogInfoMapper;
import com.minlia.module.audit.service.AuditLogInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuditLogInfoServiceImpl implements AuditLogInfoService {

    @Resource
    private AuditLogInfoMapper auditLogInfoMapper;

    @Override
    public int insertSelective(AuditLogInfo auditLogInfo) {
        return auditLogInfoMapper.insertSelective(auditLogInfo);
    }

    @Override
    public int updateByPrimaryKeySelective(AuditLogInfo auditLogInfo) {
        return auditLogInfoMapper.updateByPrimaryKeySelective(auditLogInfo);
    }

    @Override
    public PageInfo<AuditLogInfo> queryPage(AuditLogInfoQRO qro) {
        return PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(() -> auditLogInfoMapper.selectByAll(qro));
    }
}
