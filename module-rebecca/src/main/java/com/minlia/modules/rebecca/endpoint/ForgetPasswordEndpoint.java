package com.minlia.modules.rebecca.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.modules.rebecca.bean.to.PasswordResetTO;
import com.minlia.modules.rebecca.service.UserPasswordService;
import com.minlia.modules.rebecca.bean.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by will on 6/19/17.
 */
@Api(tags = "System Password", description = "密码")
@RestController
@RequestMapping(value = ApiPrefix.API+"user/password")
public class ForgetPasswordEndpoint {

    @Autowired
    UserPasswordService userPasswordService;

    @AuditLog(value = "change password")
    @ApiOperation(value = "忘记密码", notes = "忘记密码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "forget", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response resetPassword(@Valid @RequestBody PasswordResetTO to) {
        User entity = userPasswordService.forget(to);
        return Response.success(entity);
    }

}
