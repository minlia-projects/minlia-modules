package com.minlia.modules.rbac.backend.password.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.rbac.backend.password.body.ResetPasswordRequestBody;
import com.minlia.modules.rbac.backend.password.service.UserPasswordService;
import com.minlia.modules.rbac.backend.user.entity.User;
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
public class ResetPasswordEndpoint {

    @Autowired
    UserPasswordService userPasswordService;

    @ApiOperation(value = "忘记密码", notes = "忘记密码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "forget", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody resetPassword(@Valid @RequestBody ResetPasswordRequestBody body) {
        User entity = userPasswordService.resetPassword(body);
        return SuccessResponseBody.builder().payload(entity).build();
    }

}
