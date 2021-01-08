package com.minlia.module.lov.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.i18n.util.LocaleUtils;
import com.minlia.module.lov.bean.LovValueQro;
import com.minlia.module.lov.entity.SysLovItemEntity;
import com.minlia.module.lov.service.LovValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * LOV值集表 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-08-21
 */
@Api(tags = "System Lov Value", description = "LOV值")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "lov/value")
public class LovValueOpenController {

    private final LovValueService lovValueService;

    public LovValueOpenController(LovValueService lovValueService) {
        this.lovValueService = lovValueService;
    }

    @AuditLog(value = "query a lov value by id", type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response one(@PathVariable Long id) {
        return Response.success(lovValueService.getById(id));
    }

    @AuditLog(value = "query lov values by query request body as list", type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@RequestBody LovValueQro qro) {
        qro.setDisFlag(false);
        qro.setLocale(LocaleUtils.locale());
        LambdaQueryWrapper<SysLovItemEntity> queryWrapper = new QueryWrapper<SysLovItemEntity>().lambda()
                .setEntity(DozerUtils.map(qro, SysLovItemEntity.class)).last(qro.getOrderBy());
        return Response.success(lovValueService.list(queryWrapper));
    }

    @AuditLog(value = "query lov values by query request body as paginated result", type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response paginated(@RequestBody LovValueQro qro) {
        qro.setDisFlag(false);
        qro.setLocale(LocaleUtils.locale());
        LambdaQueryWrapper<SysLovItemEntity> queryWrapper = new QueryWrapper<SysLovItemEntity>().lambda()
                .setEntity(DozerUtils.map(qro, SysLovItemEntity.class)).last(qro.getOrderBy());
        Page<SysLovItemEntity> page = new Page<>(qro.getPageNumber(), qro.getPageSize());
        return Response.success(lovValueService.page(page, queryWrapper));
    }

}