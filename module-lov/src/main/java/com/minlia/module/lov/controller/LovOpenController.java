package com.minlia.module.lov.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.lov.bean.LovQro;
import com.minlia.module.lov.entity.SysLovEntity;
import com.minlia.module.lov.service.LovService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping(value = ApiPrefix.OPEN + "lov")
public class LovOpenController {

    private final LovService lovService;

    public LovOpenController(LovService lovService) {
        this.lovService = lovService;
    }

    @AuditLog(value = "query a lov by id", type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response one(@PathVariable Long id) {
        return Response.success(lovService.getById(id));
    }

    @AuditLog(value = "query lovs by query request body as list", type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@RequestBody LovQro qro) {
        qro.setDisFlag(false);
        LambdaQueryWrapper<SysLovEntity> queryWrapper = new QueryWrapper<SysLovEntity>().lambda()
                .setEntity(DozerUtils.map(qro, SysLovEntity.class));
        return Response.success(lovService.list(queryWrapper));
    }

    @AuditLog(value = "query lovs by query request body as list", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('system.lov.search')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response paginated(@RequestBody LovQro qro) {
        qro.setDisFlag(false);
        LambdaQueryWrapper<SysLovEntity> queryWrapper = new QueryWrapper<SysLovEntity>().lambda()
                .setEntity(DozerUtils.map(qro, SysLovEntity.class));
        return Response.success(lovService.page(qro.getPage(), queryWrapper));
    }


}