package com.minlia.module.email.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.email.bean.EmailHtmlSendRo;
import com.minlia.module.email.bean.EmailRecordQro;
import com.minlia.module.email.bean.EmailRichtextSendRo;
import com.minlia.module.email.constant.EmailConstants;
import com.minlia.module.email.entity.EmailRecordEntity;
import com.minlia.module.email.service.EmailRecordService;
import com.minlia.module.email.service.EmailService;
import com.minlia.module.i18n.enums.LocaleEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 邮件记录 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-08-25
 */
@Api(tags = "System Email", description = "邮件")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "email")
public class EmailRecordController {

    private final EmailService emailService;
    private final EmailRecordService emailRecordService;

    public EmailRecordController(EmailService emailService, EmailRecordService emailRecordService) {
        this.emailService = emailService;
        this.emailRecordService = emailRecordService;
    }

    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + EmailConstants.Authorize.CREATE + "')")
    @ApiOperation(value = "HTML")
    @PostMapping(value = "send/html")
    public Response html(@Valid @RequestBody EmailHtmlSendRo ro) {
        EmailRecordEntity entity = emailService.sendHtmlMail(null, ro.getTo(), ro.getSubject(), ro.getContent(), null, null, LocaleEnum.valueOf(LocaleContextHolder.getLocale().toString()));
        return Response.success(entity);
    }

    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + EmailConstants.Authorize.CREATE + "')")
    @ApiOperation(value = "RICKTEXT")
    @PostMapping(value = "send/richtext")
    public Response richtext(@Valid @RequestBody EmailRichtextSendRo ro) {
        EmailRecordEntity entity = emailService.sendRichtextMail(ro.getTo(), ro.getTemplateCode(), ro.getVariables());
        return Response.success(entity);
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + EmailConstants.Authorize.SEARCH + "')")
    @ApiOperation(value = "单个查询")
    @PostMapping(value = "one")
    public Response one(@Valid @PathVariable EmailRecordQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, EmailRecordEntity.class));
        return Response.success(emailRecordService.getOne(queryWrapper));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + EmailConstants.Authorize.SEARCH + "')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody EmailRecordQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, EmailRecordEntity.class));
        return Response.success(emailRecordService.list(queryWrapper));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + EmailConstants.Authorize.SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody EmailRecordQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, EmailRecordEntity.class));
        return Response.success(emailRecordService.page(qro.getPage(), queryWrapper));
    }

}

