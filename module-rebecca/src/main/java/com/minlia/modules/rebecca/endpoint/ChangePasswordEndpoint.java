package com.minlia.modules.rebecca.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.modules.rebecca.bean.to.PasswordByCaptchaChangeTO;
import com.minlia.modules.rebecca.bean.to.PasswordByRawPasswordChangeTO;
import com.minlia.modules.rebecca.service.UserPasswordService;
import com.minlia.modules.rebecca.bean.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "System Password", description = "密码")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "user/password")
public class ChangePasswordEndpoint {

    @Autowired
    private UserPasswordService userPasswordService;

    @AuditLog(value = "update password by raw password")
    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "根据原密码修改", notes = "修改密码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "raw", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response changePasswordMode1(@Valid @RequestBody PasswordByRawPasswordChangeTO body) {
        User entity = userPasswordService.change(body);
        return Response.success();
    }

    @AuditLog(value = "update password by otp")
    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "根据验证码修改", notes = "修改密码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "captcha", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response changePasswordMode2(@Valid @RequestBody PasswordByCaptchaChangeTO body) {
        User entity = userPasswordService.change(body);
        return Response.success();
    }

}
