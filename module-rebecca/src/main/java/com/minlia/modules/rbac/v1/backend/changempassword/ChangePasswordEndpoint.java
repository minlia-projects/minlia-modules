package com.minlia.modules.rbac.v1.backend.changempassword;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.rbac.domain.User;
import com.minlia.modules.rbac.v1.backend.changempassword.body.ChangePasswordByRawPasswordRequestBody;
import com.minlia.modules.rbac.v1.backend.changempassword.body.ChangePasswordBySecurityCodeRequestBody;
import com.minlia.modules.rbac.v1.backend.password.UserPasswordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * Created by will on 6/19/17.
 * 用户修改密码
 */
@RestController
@RequestMapping(value = ApiPrefix.V1+"user/changePassword")
@Api(tags = "用户", description = "用户修改密码")
@Slf4j
public class ChangePasswordEndpoint {

    @Autowired
    UserPasswordService userPasswordService;

    /**
     * 根据原密码修改密码
     * @param request
     * @param response
     * @return
     */
    @PreAuthorize(value = "hasAnyAuthority('"+UserPasswordService.USER_CHANGE_PASSWORD+"')")
    @ApiOperation(value = "根据原密码修改密码", notes = "修改密码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/raw", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody changePasswordMode1(@RequestBody ChangePasswordByRawPasswordRequestBody body, HttpServletRequest request, HttpServletResponse response) {
        User entity=userPasswordService.changePassword(body);
        entity.setPassword("******");
        return SuccessResponseBody.builder().payload(entity).build();
//        return SuccessResponseBody.builder().message("OK").build();
    }
    /**
     * 根据验证码修改密码
     * @param request
     * @param response
     * @return
     */
    @PreAuthorize(value = "hasAnyAuthority('"+UserPasswordService.USER_CHANGE_PASSWORD+"')")
    @ApiOperation(value = "根据验证码修改密码", notes = "修改密码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/code", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody changePasswordMode2(@RequestBody ChangePasswordBySecurityCodeRequestBody body, HttpServletRequest request, HttpServletResponse response) {
        User entity=userPasswordService.changePassword(body);
        entity.setPassword("******");
        return SuccessResponseBody.builder().payload(entity).build();
//        return SuccessResponseBody.builder().message("OK").build();
    }

}
