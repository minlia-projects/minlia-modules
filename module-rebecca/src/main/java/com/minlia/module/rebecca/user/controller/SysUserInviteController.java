package com.minlia.module.rebecca.user.controller;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import com.minlia.module.rebecca.user.bean.SysUserRelationQro;
import com.minlia.module.rebecca.user.constant.SysUserCode;
import com.minlia.module.rebecca.user.service.SysUserRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-08-24
 */
@Api(tags = "System User invite", description = "用户邀请")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "user/invite")
@RequiredArgsConstructor
public class SysUserInviteController {

    private final SysUserRelationService sysUserRelationService;

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysUserCode.READ + "')")
    @ApiOperation(value = "me")
    @PostMapping(value = "me")
    public Response me(@Valid @RequestBody SysUserRelationQro qro) {
        qro.setAncestor(SecurityContextHolder.getUid());
        return Response.success(sysUserRelationService.detailsPage(qro));
    }

}