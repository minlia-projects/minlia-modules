package com.minlia.module.email.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.email.bean.EmailSenderCro;
import com.minlia.module.email.bean.EmailSenderQro;
import com.minlia.module.email.bean.EmailSenderUro;
import com.minlia.module.email.constant.EmailConstants;
import com.minlia.module.email.entity.EmailRecordEntity;
import com.minlia.module.email.entity.EmailSenderEntity;
import com.minlia.module.email.service.EmailSenderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 邮件-发件人 前端控制器
 * </p>
 *
 * @author garen
 * @since 2021-01-11
 */
@Api(tags = "System Email Sender", description = "邮件-发件人")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "email/sender")
public class EmailSenderController {

    private final EmailSenderService emailFromService;

    public EmailSenderController(EmailSenderService emailFromService) {
        this.emailFromService = emailFromService;
    }

    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + EmailConstants.Authorize.CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping
    public Response create(@Valid @RequestBody EmailSenderCro cro) {
        return Response.success(emailFromService.create(cro));
    }

    @AuditLog(type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + EmailConstants.Authorize.UPDATE + "')")
    @ApiOperation(value = "修改")
    @PutMapping
    public Response update(@Valid @RequestBody EmailSenderUro uro) {
        return Response.success(emailFromService.update(uro));
    }

    @AuditLog(type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + EmailConstants.Authorize.DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(emailFromService.delete(id));
    }

    @AuditLog(type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + EmailConstants.Authorize.UPDATE + "')")
    @ApiOperation(value = "禁用")
    @DeleteMapping(value = "disable/{id}")
    public Response disable(@PathVariable Long id) {
        return Response.success(emailFromService.disable(id));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + EmailConstants.Authorize.SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response one(@PathVariable Long id) {
        return Response.success(emailFromService.getById(id));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + EmailConstants.Authorize.SEARCH + "')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody EmailSenderQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, EmailRecordEntity.class)).last(qro.getOrderBy());
        return Response.success(emailFromService.list(queryWrapper));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + EmailConstants.Authorize.SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody EmailSenderQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, EmailSenderEntity.class)).last(qro.getOrderBy());
        return Response.success(emailFromService.page(qro.getPage(), queryWrapper));
    }

}