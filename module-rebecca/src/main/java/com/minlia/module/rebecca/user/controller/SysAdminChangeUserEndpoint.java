package com.minlia.module.rebecca.user.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import com.minlia.module.rebecca.user.bean.SysChangeCellphoneRo;
import com.minlia.module.rebecca.user.constant.SysUserCode;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author garen
 */
@Api(tags = "System User Change", description = "用户信息变更")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "admin/user/change")
public class SysAdminChangeUserEndpoint {

    private final SysUserService sysUserService;

    public SysAdminChangeUserEndpoint(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @AuditLog(type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysUserCode.UPDATE + "')")
    @ApiOperation(value = "手机号码")
    @PutMapping(value = "cellphone")
    public Response cellphone(@Valid @RequestBody SysChangeCellphoneRo cellphoneRo) {
        SysUserEntity entity = sysUserService.getOne(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getId, SecurityContextHolder.getUid()));
        sysUserService.changeCellphone(entity, cellphoneRo.getAreaCode() + cellphoneRo.getCellphone(), cellphoneRo.getVcode());
        return Response.success();
    }

    @AuditLog(type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysUserCode.UPDATE + "')")
    @ApiOperation(value = "邮箱")
    @PutMapping(value = "email/{email}/{vcode}")
    public Response email(@PathVariable String email, @PathVariable String vcode) {
        SysUserEntity entity = sysUserService.getOne(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getId, SecurityContextHolder.getUid()));
        sysUserService.changeEmail(entity, email, vcode);
        return Response.success();
    }

}