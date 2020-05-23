package com.minlia.modules.rebecca.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.OperationTypeEnum;
import com.minlia.modules.rebecca.bean.domain.Organization;
import com.minlia.modules.rebecca.constant.RebeccaSecurityConstant;
import com.minlia.modules.rebecca.service.OrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author garen
 * @version 1.0
 * @description 组织管理-前端控制器
 * @date 2019/8/12 11:41 AM
 */
@RestController
@RequestMapping(ApiPrefix.V1 + "organization")
@Api(tags = "System Organization", value = "organization", description = "组织")
public class OrganizationEndpoint {

    @Autowired
    private OrganizationService organizationService;

    @AuditLog(value = "create organization", type = OperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.ORGANIZATION_CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping()
    public Response create(@Valid @RequestBody Organization organization) {
        return Response.success(organizationService.insertSelective(organization));
    }

    @AuditLog(value = "update organization", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.ORGANIZATION_UPDATE + "')")
    @ApiOperation(value = "更新")
    @PutMapping()
    public Response update(@Valid @RequestBody Organization organization) {
        return Response.success(organizationService.updateByPrimaryKeySelective(organization));
    }

    @AuditLog(value = "delete organization by code", type = OperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.ORGANIZATION_DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        organizationService.deleteByPrimaryKey(id);
        return Response.success(SystemCode.Message.DELETE_SUCCESS);
    }

    @AuditLog(value = "query organization by id", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.ORGANIZATION_SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response queryById(@PathVariable Long id) {
        return Response.success(organizationService.selectByPrimaryKey(id));
    }

    @AuditLog(value = "query organization tree", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.ORGANIZATION_SEARCH + "')")
    @ApiOperation(value = "树形菜单")
    @GetMapping(value = "tree")
    public Response getTree() {
        return Response.success(organizationService.selectTree());
    }

    @AuditLog(value = "query organization tree", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.ORGANIZATION_SEARCH + "')")
    @ApiOperation(value = "用户树形菜单")
    @GetMapping(value = "/tree/me")
    public Response getUserTree() {
        return Response.success(organizationService.getUserTree());
    }

}
