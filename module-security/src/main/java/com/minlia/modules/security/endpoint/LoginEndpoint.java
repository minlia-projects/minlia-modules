package com.minlia.modules.security.endpoint;

import com.minlia.modules.security.authentication.credential.LoginCredentials;
import com.minlia.modules.security.constant.SecurityConstant;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by will on 7/21/17.
 * This is just a fake control for springfox-swagger2 ro generate api-docs
 */
@CrossOrigin
@RestController
@RequestMapping(value = "auth/login")
public class LoginEndpoint {

    //    @AuditLog(value = "login", type = OperationTypeEnum.INFO)
//    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping(value = "")
    public void login(@Valid @RequestBody LoginCredentials credential) {
    }

    @RequestMapping(value = "check", method = RequestMethod.GET)
    public void session(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(SecurityConstant.SID, request.getSession().getId());
    }

}
