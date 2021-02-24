package com.minlia.module.account.corporate.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.account.corporate.bean.CorporateAccountSro;
import com.minlia.module.account.corporate.constant.CorporateAccountConstants;
import com.minlia.module.account.corporate.entity.CorporateAccountEntity;
import com.minlia.module.account.corporate.service.CorporateAccountService;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.data.bean.QueryRequest;
import com.minlia.module.data.entity.BaseQueryEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 对公帐号 前端控制器
 * </p>
 *
 * @author garen
 * @since 2021-02-18
 */
@Api(tags = "System Corporate Account", description = "对公帐号")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "corporate/account")
public class CorporateAccountController {

    private final CorporateAccountService corporateAccountService;

    public CorporateAccountController(CorporateAccountService corporateAccountService) {
        this.corporateAccountService = corporateAccountService;
    }

    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + CorporateAccountConstants.Authorize.CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping
    public Response create(@Validated @RequestBody CorporateAccountSro sro) {
        return Response.success(corporateAccountService.save(sro));
    }

    @AuditLog(type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + CorporateAccountConstants.Authorize.UPDATE + "')")
    @ApiOperation(value = "修改")
    @PutMapping
    public Response update(@Validated @RequestBody CorporateAccountSro sro) {
        return Response.success(corporateAccountService.save(sro));
    }

    @AuditLog(type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + CorporateAccountConstants.Authorize.DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(corporateAccountService.removeById(id));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + CorporateAccountConstants.Authorize.SELECT + "')")
    @ApiOperation(value = "默认")
    @GetMapping(value = "default/{currency}")
    public Response getDefault(@PathVariable String currency) {
        return Response.success(corporateAccountService.getOne(Wrappers.<CorporateAccountEntity>lambdaQuery().eq(CorporateAccountEntity::getCurrency, currency).eq(CorporateAccountEntity::getDefFlag, true), false));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + CorporateAccountConstants.Authorize.SELECT + "')")
    @ApiOperation(value = "详情")
    @GetMapping(value = "{id}")
    public Response select(@PathVariable Long id) {
        return Response.success(corporateAccountService.getById(id));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + CorporateAccountConstants.Authorize.SELECT + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody QueryRequest queryRequest) {
        return Response.success(corporateAccountService.page(queryRequest.getPage()));
    }

}