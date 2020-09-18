package com.minlia.module.rebecca.user.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.AuditOperationTypeEnum;
import com.minlia.module.rebecca.user.constant.SysUserCode;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author garen
 */
@Api(tags = "System User Change", description = "用户信息变更")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "admin/user/change")
public class SysAdminChangeUserEndpoint {

    @Autowired
    private SysUserService sysUserService;

    @AuditLog(value = "admin change user cellphone", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysUserCode.UPDATE + "')")
    @ApiOperation(value = "手机号码", notes = "手机号码", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "cellphone/{uid}/{cellphone}/{vcode}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response cellphone(@PathVariable Long uid, @PathVariable String cellphone, @PathVariable String vcode) {
        SysUserEntity entity = sysUserService.getOne(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getId, uid));
        sysUserService.changeCellphone(entity, cellphone, vcode);
        return Response.success();
    }

    @AuditLog(value = "admin change user email", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysUserCode.UPDATE + "')")
    @ApiOperation(value = "邮箱", notes = "邮箱", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "email/{uid}/{email}/{vcode}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response email(@PathVariable Long uid, @PathVariable String email, @PathVariable String vcode) {
        SysUserEntity entity = sysUserService.getOne(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getId, uid));
        sysUserService.changeEmail(entity, email, vcode);
        return Response.success();
    }

}