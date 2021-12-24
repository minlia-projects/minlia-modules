package com.minlia.module.member.controller;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.member.bean.SysMemberQro;
import com.minlia.module.member.constant.SysMemberConstants;
import com.minlia.module.member.service.SysMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author garen
 */
@Api(tags = "System Member", description = "会员")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "member")
@RequiredArgsConstructor
public class SysMemberController {

    private final SysMemberService sysMemberService;

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