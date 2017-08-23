package com.minlia.modules.rbac.v1.openapi.resetpassword;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.rbac.domain.User;
import com.minlia.modules.rbac.v1.backend.password.UserPasswordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by will on 6/19/17.
 */
@RestController
@RequestMapping(value = ApiPrefix.API+"user/resetPassword")
@Api(tags = "用户", description = "用户忘记密码")
@Slf4j
public class ResetPasswordEndpoint {

    @Autowired
    UserPasswordService userPasswordService;

    /**
     * 用户注册
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "忘记密码", notes = "忘记密码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody resetPassword(@RequestBody ResetPasswordRequestBody body, HttpServletRequest request, HttpServletResponse response) {
        User entity=userPasswordService.resetPassword(body);
        entity.setPassword("******");
        return SuccessResponseBody.builder().payload(entity).build();
    }

}
