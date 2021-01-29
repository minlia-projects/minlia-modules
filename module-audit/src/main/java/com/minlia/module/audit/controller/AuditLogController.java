package com.minlia.module.audit.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.entity.AuditLogEntity;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.audit.service.AuditLogService;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.bean.AuditLogQro;
import com.minlia.module.audit.constant.AuditConstants;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.modules.security.context.MinliaSecurityContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 审计日志 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-08-21
 */
@Api(tags = "System Audit Log", description = "审计日志")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "/audit", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @PreAuthorize(value = "hasAnyAuthority('" + AuditConstants.SEARCH + "')")
    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@RequestBody AuditLogQro qro) {
        AuditLogEntity entity = DozerUtils.map(qro, AuditLogEntity.class);
        LambdaQueryWrapper<AuditLogEntity> queryWrapper = new QueryWrapper<AuditLogEntity>().lambda().setEntity(entity);
        Page<AuditLogEntity> page = new Page<>(qro.getPageNumber(), qro.getPageSize());
        return Response.success(auditLogService.page(page, queryWrapper));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + AuditConstants.READ + "')")
    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page/me")
    public Response myPage(@RequestBody AuditLogQro qro) {
        AuditLogEntity entity = DozerUtils.map(qro, AuditLogEntity.class);
        entity.setCreateBy(MinliaSecurityContextHolder.getUid());
        LambdaQueryWrapper<AuditLogEntity> queryWrapper = new QueryWrapper<AuditLogEntity>().lambda().setEntity(entity);
        Page<AuditLogEntity> page = new Page<>(qro.getPageNumber(), qro.getPageSize());
        return Response.success(auditLogService.page(page, queryWrapper));
    }

}

