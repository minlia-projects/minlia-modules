package com.minlia.module.member.controller;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.member.bean.SysMemberQro;
import com.minlia.module.member.constant.SysMemberConstants;
import com.minlia.module.member.service.SysMemberService;
import com.minlia.module.realname.bean.SysRealNameCro;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 会员 前端控制器
 * </p>
 *
 * @author garen
 * @since 2021-12-28
 */
@Api(tags = "System member", description = "会员")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "member")
@RequiredArgsConstructor
public class SysMemberController {

    private final SysMemberService sysMemberService;

    @PreAuthorize(value = "hasAnyAuthority('" + SysMemberConstants.Authorize.AUTH + "')")
    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @ApiOperation(value = "实名")
    @PostMapping(value = "real-name")
    public Response authentication(@Valid @RequestBody SysRealNameCro cro) {
        return sysMemberService.realName(cro);
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysMemberConstants.Authorize.READ + "')")
    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "我的")
    @GetMapping(value = "me")
    public Response me() {
        return Response.success(sysMemberService.me());
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysMemberConstants.Authorize.SELECT + "')")
    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody SysMemberQro qro) {
        return Response.success(sysMemberService.page(qro));
    }

}