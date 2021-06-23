package com.minlia.module.lov.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.i18n.util.LocaleUtils;
import com.minlia.module.lov.bean.LovValueQro;
import com.minlia.module.lov.entity.SysLovEntity;
import com.minlia.module.lov.entity.SysLovItemEntity;
import com.minlia.module.lov.service.LovService;
import com.minlia.module.lov.service.LovValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
@RequiredArgsConstructor
public class LovValueOpenController {

    private final LovService lovService;
    private final LovValueService lovValueService;

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
        if (StringUtils.isNotBlank(qro.getLovCode())) {
            qro.setLovId(lovService.getOne(Wrappers.<SysLovEntity>lambdaQuery().select(SysLovEntity::getId).eq(SysLovEntity::getCode, qro.getLovCode())).getId());
        }
        LambdaQueryWrapper<SysLovItemEntity> lambdaQueryWrapper = Wrappers.<SysLovItemEntity>lambdaQuery().setEntity(DozerUtils.map(qro, SysLovItemEntity.class));
        return Response.success(lovValueService.list(lambdaQueryWrapper));
    }

    @AuditLog(value = "query lov values by query request body as paginated result", type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response paginated(@RequestBody LovValueQro qro) {
        qro.setDisFlag(false);
        qro.setLocale(LocaleUtils.locale());
        if (StringUtils.isNotBlank(qro.getLovCode())) {
            qro.setLovId(lovService.getOne(Wrappers.<SysLovEntity>lambdaQuery().select(SysLovEntity::getId).eq(SysLovEntity::getCode, qro.getLovCode())).getId());
        }
        LambdaQueryWrapper<SysLovItemEntity> lambdaQueryWrapper = Wrappers.<SysLovItemEntity>lambdaQuery().setEntity(DozerUtils.map(qro, SysLovItemEntity.class));
        return Response.success(lovValueService.page(qro.getPage(), lambdaQueryWrapper));
    }

}