package com.minlia.module.rebecca.controller;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.rebecca.bean.SysOrganizationCro;
import com.minlia.module.rebecca.bean.SysOrganizationUro;
import com.minlia.module.rebecca.constant.RebeccaConstant;
import com.minlia.module.rebecca.service.SysOrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 组织 前端控制器
 * </p>
 *
 * @author garen
 * @since 2021-03-02
 */
@Api(tags = "System Organization", description = "组织")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "org")
public class SysOrgController {

    private final SysOrgService sysOrgService;

    public SysOrgController(SysOrgService sysOrgService) {
        this.sysOrgService = sysOrgService;
    }

    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaConstant.Authorize.Organization.CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping
    public Response create(@Valid @RequestBody SysOrganizationCro cro) {
        return Response.success(sysOrgService.create(cro));
    }

    @AuditLog(type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaConstant.Authorize.Organization.UPDATE + "')")
    @ApiOperation(value = "修改")
    @PutMapping
    public Response update(@Valid @RequestBody SysOrganizationUro uro) {
        return Response.success(sysOrgService.update(uro));
    }

    @AuditLog(type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaConstant.Authorize.Organization.DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response update(@PathVariable Long id) {
        return Response.success(sysOrgService.delete(id));
    }

//    /**
//     * 返回树形菜单集合
//     *
//     * @return 树形菜单
//     */
//    @GetMapping(value = "/tree")
//    public Response listDeptTrees() {
//        return Response.success(sysOrgService.listDeptTrees());
//    }
//
//    /**
//     * 返回当前用户树形菜单集合
//     *
//     * @return 树形菜单
//     */
//    @GetMapping(value = "/user-tree")
//    public Response listCurrentUserDeptTrees() {
//        return Response.success(sysOrgService.listCurrentUserDeptTrees());
//    }
//
//    /**
//     * 根据部门名查询部门信息
//     *
//     * @param deptname 部门名
//     * @return
//     */
//    @GetMapping("/details/{deptname}")
//    public Response user(@PathVariable String deptname) {
//        SysDept condition = new SysDept();
//        condition.setName(deptname);
//        return Response.success(sysOrgService.getOne(new QueryWrapper<>(condition)));
//    }

}