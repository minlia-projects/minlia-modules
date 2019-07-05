package com.minlia.modules.rebecca.endpoint;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.modules.rebecca.bean.domain.User;
import com.minlia.modules.rebecca.constant.RebeccaSecurityConstant;
import com.minlia.modules.rebecca.service.UserQueryService;
import com.minlia.modules.rebecca.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "System User Change", description = "用户信息变更")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "admin/user/change")
public class AdminChangeUserEndpoint {

    @Autowired
    private UserService userService;

    @Autowired
    private UserQueryService userQueryService;

    @AuditLog(value = "admin change user cellphone")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_UPDATE + "')")
    @ApiOperation(value = "手机号码", notes = "手机号码", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "cellphone/{guid}/{cellphone}/{captcha}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response cellphone(@PathVariable String guid, @PathVariable String cellphone, @PathVariable String captcha) {
        User user = userQueryService.queryByGuidAndNotNull(guid);
        userService.changeCellphone(user, cellphone, captcha);
        return Response.success();
    }

    @AuditLog(value = "admin change user email")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_UPDATE + "')")
    @ApiOperation(value = "邮箱", notes = "邮箱", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "email/{guid}/{email}/{captcha}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response email(@PathVariable String guid, @PathVariable String email, @PathVariable String captcha) {
        User user = userQueryService.queryByGuidAndNotNull(guid);
        userService.changeEmail(user, email, captcha);
        return Response.success();
    }

}
