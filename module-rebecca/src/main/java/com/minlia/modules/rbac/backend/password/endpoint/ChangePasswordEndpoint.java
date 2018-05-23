package com.minlia.modules.rbac.backend.password.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.rbac.backend.password.body.ChangePasswordByRawPasswordRequestBody;
import com.minlia.modules.rbac.backend.password.body.ChangePasswordByCaptchaRequestBody;
import com.minlia.modules.rbac.backend.password.service.UserPasswordService;
import com.minlia.modules.rbac.backend.user.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "System Password", description = "密码")
@RestController
@RequestMapping(value = ApiPrefix.V1+"user/password")
public class ChangePasswordEndpoint {

    @Autowired
    private UserPasswordService userPasswordService;

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "根据原密码修改", notes = "修改密码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "raw", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody changePasswordMode1(@RequestBody ChangePasswordByRawPasswordRequestBody body) {
        User entity = userPasswordService.change(body);
        return SuccessResponseBody.builder().build();
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "根据验证码修改", notes = "修改密码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "captcha", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody changePasswordMode2(@RequestBody ChangePasswordByCaptchaRequestBody body) {
        User entity=userPasswordService.change(body);
        return SuccessResponseBody.builder().build();
    }

}
