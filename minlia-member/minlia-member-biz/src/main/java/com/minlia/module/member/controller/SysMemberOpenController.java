package com.minlia.module.member.controller;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.riskcontrol.annotation.NoRepeatSubmit;
import com.minlia.module.member.service.SysMemberService;
import com.minlia.module.rebecca.user.bean.MemberRegisterRo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * @author garen
 */
@Api(tags = "System Member Open", description = "会员")
@RestController
@RequestMapping(value = ApiPrefix.API + "member")
@RequiredArgsConstructor
public class SysMemberOpenController {

    private final SysMemberService sysMemberService;

    @NoRepeatSubmit
    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @ApiOperation(value = "注册")
    @PostMapping(value = "register")
    public Response register(@Valid @RequestBody MemberRegisterRo registerRo) {
        return sysMemberService.register(registerRo);
    }

}