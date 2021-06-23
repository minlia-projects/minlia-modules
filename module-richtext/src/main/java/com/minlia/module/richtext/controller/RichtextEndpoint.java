package com.minlia.module.richtext.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.richtext.bean.RichtextCro;
import com.minlia.module.richtext.bean.RichtextQro;
import com.minlia.module.richtext.bean.RichtextUro;
import com.minlia.module.richtext.constant.RichtextConstants;
import com.minlia.module.richtext.entity.RichtextEntity;
import com.minlia.module.richtext.service.RichtextService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 富文本 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-08-25
 */
@Api(tags = "System Rich Text")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "richtext")
public class RichtextEndpoint {

    private final RichtextService richtextService;

    public RichtextEndpoint(RichtextService richtextService) {
        this.richtextService = richtextService;
    }

    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + RichtextConstants.CREATE + "')")
    @ApiOperation(value = "create")
    @PostMapping
    public Response create(@Valid @RequestBody RichtextCro cro) {
        return Response.success(richtextService.save(DozerUtils.map(cro, RichtextEntity.class)));
    }

    @AuditLog(type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + RichtextConstants.UPDATE + "')")
    @ApiOperation(value = "update")
    @PutMapping
    public Response update(@Valid @RequestBody RichtextUro uro) {
        return Response.success(richtextService.updateById(DozerUtils.map(uro, RichtextEntity.class)));
    }

    @AuditLog(type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + RichtextConstants.DELETE + "')")
    @ApiOperation(value = "delete")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(richtextService.removeById(id));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + RichtextConstants.SEARCH + "')")
    @ApiOperation(value = "id")
    @GetMapping(value = "{id}")
    public Response findOne(@PathVariable Long id) {
        return Response.success(richtextService.getById(id));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + RichtextConstants.SEARCH + "')")
    @ApiOperation(value = "list")
    @PostMapping(value = "list")
    public Response list(@RequestBody RichtextQro qro) {
        LambdaQueryWrapper<RichtextEntity> queryWrapper = new QueryWrapper<RichtextEntity>().lambda()
                .setEntity(DozerUtils.map(qro, RichtextEntity.class));
        return Response.success(richtextService.list(queryWrapper));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + RichtextConstants.SEARCH + "')")
    @ApiOperation(value = "page")
    @PostMapping(value = "page")
    public Response page(@RequestBody RichtextQro qro) {
        LambdaQueryWrapper<RichtextEntity> queryWrapper = new QueryWrapper<RichtextEntity>().lambda()
                .setEntity(DozerUtils.map(qro, RichtextEntity.class));
        return Response.success(richtextService.page(qro.getPageNumber(), queryWrapper));
    }

}