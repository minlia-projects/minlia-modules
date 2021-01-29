package com.minlia.module.lov.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.lov.bean.LovQro;
import com.minlia.module.lov.bean.LovSro;
import com.minlia.module.lov.entity.SysLovEntity;
import com.minlia.module.lov.service.LovService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * LOV表 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-08-21
 */
@Api(tags = "System Lov", description = "LOV值集")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "lov")
public class LovController {

    private final LovService lovService;

    public LovController(LovService lovService) {
        this.lovService = lovService;
    }

    @AuditLog(value = "create a lov", type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('system.lov.create')")
    @ApiOperation(value = "创建")
    @PostMapping(value = "")
    public Response create(@Valid @RequestBody LovSro sro) {
        return Response.success(lovService.save(DozerUtils.map(sro, SysLovEntity.class)));
    }

    @AuditLog(value = "update a lov", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('system.lov.update')")
    @ApiOperation(value = "更新")
    @PutMapping(value = "")
    public Response update(@Valid @RequestBody LovSro sro) {
        return Response.success(lovService.updateById(DozerUtils.map(sro, SysLovEntity.class)));
    }

    @AuditLog(value = "toggle a lov status by id", type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('system.lov.delete')")
    @ApiOperation(value = "启用/禁用")
    @PostMapping(value = "disable/{id}")
    public Response disable(@PathVariable Long id) {
        return Response.success(lovService.disable(id));
    }

    @AuditLog(value = "delete a lov status by id", type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('system.lov.delete')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(SystemCode.Message.DELETE_SUCCESS, lovService.removeById(id));
    }

    //    @AuditLog(value = "query a lov by id", type = AuditOperationTypeEnum.SELECT)
//    @PreAuthorize(value = "hasAnyAuthority('system.lov.search')")
//    @ApiOperation(value = "ID查询")
//    @GetMapping(value = "{id}")
//    public Response one(@PathVariable Long id) {
//        return Response.success(lovService.getById(id));
//    }
//
    @AuditLog(value = "query lovs by query request body as list", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('system.lov.search')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@RequestBody LovQro qro) {
        LambdaQueryWrapper<SysLovEntity> queryWrapper = new QueryWrapper<SysLovEntity>().lambda()
                .setEntity(DozerUtils.map(qro, SysLovEntity.class));
        return Response.success(lovService.list(queryWrapper));
    }

    @AuditLog(value = "query lovs by query request body as list", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('system.lov.search')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response paginated(@RequestBody LovQro qro) {
        LambdaQueryWrapper<SysLovEntity> queryWrapper = new QueryWrapper<SysLovEntity>().lambda()
                .setEntity(DozerUtils.map(qro, SysLovEntity.class));
        Page<SysLovEntity> page = new Page<>(qro.getPageNumber(), qro.getPageSize());
        return Response.success(lovService.page(page, queryWrapper));
    }

}