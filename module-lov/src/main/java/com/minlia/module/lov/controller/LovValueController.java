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
import com.minlia.module.lov.bean.LovValueSro;
import com.minlia.module.lov.entity.SysLovItemEntity;
import com.minlia.module.lov.service.LovValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
@RequestMapping(value = ApiPrefix.V1 + "lov/value")
public class LovValueController {

    @Autowired
    private LovValueService lovValueService;

    @AuditLog(value = "create a lov value", type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('system.lov_value.create')")
    @ApiOperation(value = "创建")
    @PostMapping(value = "")
    public Response create(@Valid @RequestBody LovValueSro sro) {
        return Response.success(lovValueService.create(DozerUtils.map(sro, SysLovItemEntity.class)));
    }

    @AuditLog(value = "update a lov value", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('system.lov_value.update')")
    @ApiOperation(value = "更新")
    @PutMapping(value = "")
    public Response update(@Valid @RequestBody LovValueSro sro) {
        return Response.success(lovValueService.update(DozerUtils.map(sro, SysLovItemEntity.class)));
    }

    @AuditLog(value = "toggle a lov status by id", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('system.lov_value.delete')")
    @ApiOperation(value = "启用/禁用")
    @PutMapping(value = "disable/{id}")
    public Response disable(@PathVariable Long id) {
        return Response.success(lovValueService.disable(id));
    }

    @AuditLog(value = "delete a lov value by id", type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('system.lov.delete')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(lovValueService.removeById(id));
    }

    //    @PreAuthorize(value = "hasAnyAuthority('minlia.lov_value.search')")
//    @ApiOperation(value = "ID查询", httpMethod = "GET")
//    @GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response one(@PathVariable Long id) {
//        return Response.success(lovValueService.selectByPrimaryKey(id));
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('minlia.lov_value.search')")
//    @ApiOperation(value = "集合查询", httpMethod = "POST")
//    @PostMapping(value = "list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response list(@RequestBody LovValueQRO qro) {
//        return Response.success(lovValueService.selectByAll(qro));
//    }
//

    @AuditLog(value = "query lov values by query request body as paginated result", type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response paginated(@RequestBody LovValueQro qro) {
        qro.setDisFlag(false);
        qro.setLocale(LocaleUtils.locale());
        LambdaQueryWrapper<SysLovItemEntity> queryWrapper = new QueryWrapper<SysLovItemEntity>().lambda()
                .setEntity(DozerUtils.map(qro, SysLovItemEntity.class));
        Page<SysLovItemEntity> page = new Page<>(qro.getPageNumber(), qro.getPageSize());
        return Response.success(lovValueService.page(page, queryWrapper));
    }

}

