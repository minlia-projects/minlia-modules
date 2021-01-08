package com.minlia.module.rebecca.user.controller;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.module.common.property.MinliaValidProperties;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import com.minlia.module.rebecca.user.bean.SysUserChangeRo;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author garen
 */
@Api(tags = "System User Change", description = "用户信息变更")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "user/change")
public class SysUserChangeEndpoint {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private MinliaValidProperties minliaValidProperties;

    @AuditLog(value = "change cellphone", type = AuditOperationTypeEnum.UPDATE)
//    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_UPDATE + "')")
    @ApiOperation(value = "手机号码", notes = "手机号码", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(value = "cellphone/{cellphone}/{vcode}")
    public Response cellphone(@PathVariable String cellphone, @PathVariable String vcode) {
        SysUserEntity entity = SecurityContextHolder.getCurrentUser();
        sysUserService.changeCellphone(entity, cellphone, vcode);
        return Response.success();
    }

    @AuditLog(value = "change email address", type = AuditOperationTypeEnum.UPDATE)
//    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_UPDATE + "')")
    @ApiOperation(value = "邮箱", notes = "邮箱", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(value = "email/{email}/{vcode}")
    public Response email(@PathVariable String email, @PathVariable String vcode) {
        SysUserEntity entity = SecurityContextHolder.getCurrentUser();
        sysUserService.changeEmail(entity, email, vcode);
        return Response.success();
    }

    @AuditLog(value = "change nickname", type = AuditOperationTypeEnum.UPDATE)
    //    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_UPDATE + "')")
    @ApiOperation(value = "昵称")
    @PutMapping(value = "nickname/{nickname}")
    public Response nickname(@PathVariable String nickname) {
        SysUserEntity entity = SecurityContextHolder.getCurrentUser();
        return Response.success();
    }

    @AuditLog(value = "change nickname", type = AuditOperationTypeEnum.UPDATE)
    //    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_UPDATE + "')")
    @ApiOperation(value = "修改")
    @PutMapping
    public Response change(@Valid @RequestBody SysUserChangeRo changeRo) {
        SysUserEntity entity = SecurityContextHolder.getCurrentUser();
        entity.setNickname(changeRo.getNickname());
        entity.setAvatar(changeRo.getAvatar());
        sysUserService.updateById(entity);
        return Response.success();
    }

}
