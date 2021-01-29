package com.minlia.module.attachment.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.attachment.bean.AttachmentCro;
import com.minlia.module.attachment.bean.AttachmentQro;
import com.minlia.module.attachment.bean.AttachmentUro;
import com.minlia.module.attachment.constant.SysAttachmentConstant;
import com.minlia.module.attachment.entity.SysAttachmentEntity;
import com.minlia.module.attachment.service.AttachmentService;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.dozer.util.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 附件 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-08-26
 */
@Api(tags = "System Attachment", description = "附件")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "attachment")
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @AuditLog(value = "create attachment", type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysAttachmentConstant.CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping
    public Response create(@Valid @RequestBody AttachmentCro cto) {
        return Response.success(attachmentService.create(cto));
    }

    @AuditLog(value = "update attachment by id", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysAttachmentConstant.UPDATE + "')")
    @ApiOperation(value = "修改")
    @PutMapping
    public Response update(@Valid @RequestBody AttachmentUro uro) {
        return Response.success(attachmentService.saveOrUpdate(DozerUtils.map(uro, SysAttachmentEntity.class)));
    }

    @AuditLog(value = "delete attachment by id", type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysAttachmentConstant.DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(attachmentService.removeById(id));
    }

    @AuditLog(value = "select attachemnt by id", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysAttachmentConstant.SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response queryOne(@PathVariable Long id) {
        return Response.success(attachmentService.getById(id));
    }

    @AuditLog(value = "select attachemnt by relationId with relationTo", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysAttachmentConstant.SEARCH + "')")
    @ApiOperation(value = "业务查询")
    @GetMapping(value = "fbb/{relationId}/{relationTo}")
    public Response queryAllByRelationIdAndBelongsTo(@PathVariable String relationId, @PathVariable String relationTo) {
        return Response.success(attachmentService.getOne(Wrappers.<SysAttachmentEntity>lambdaQuery().eq(SysAttachmentEntity::getRelationId, relationId).eq(SysAttachmentEntity::getRelationTo, relationTo)));
    }

    @AuditLog(value = "select attachemnt by qro as list", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysAttachmentConstant.SEARCH + "')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@RequestBody AttachmentQro qro) {
        LambdaQueryWrapper<SysAttachmentEntity> queryWrapper = new QueryWrapper<SysAttachmentEntity>().lambda()
                .setEntity(DozerUtils.map(qro, SysAttachmentEntity.class))
                ;
        return Response.success(attachmentService.list(queryWrapper));
    }

    @AuditLog(value = "select attachemnt by qro as page", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysAttachmentConstant.SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "page")
    public Response page(@RequestBody AttachmentQro qro) {
        LambdaQueryWrapper<SysAttachmentEntity> queryWrapper = new QueryWrapper<SysAttachmentEntity>().lambda()
                .setEntity(DozerUtils.map(qro, SysAttachmentEntity.class))
                ;
        Page<SysAttachmentEntity> page = new Page<>(qro.getPageNumber(), qro.getPageSize());
        return Response.success(attachmentService.page(page, queryWrapper));
    }

}
