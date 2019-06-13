package com.minlia.modules.rbac.endpoint;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.module.common.constant.CommonCode;
import com.minlia.modules.rbac.bean.domain.User;
import com.minlia.modules.rbac.bean.qo.UserQO;
import com.minlia.modules.rbac.constant.UserCode;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import com.minlia.modules.rbac.service.UserQueryService;
import com.minlia.modules.rbac.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@Api(tags = "System User Change", description = "用户信息变更")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "user/change")
public class UserChangeEndpoint {

    @Autowired
    private UserService userService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private UserQueryService userQueryService;

//    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_UPDATE + "')")
    @ApiOperation(value = "手机号码", notes = "手机号码", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "cellphone/{cellphone}/{captcha}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response cellphone(@PathVariable String cellphone, @PathVariable String captcha) {
        //正则校验
        ApiAssert.state(Pattern.matches("^1[0-9]{10}$", cellphone), CommonCode.Message.CELLPHONE_FORMAT_ERROR);

        //检查手机号码是否存在
        ApiAssert.state(!userQueryService.exists(UserQO.builder().cellphone(cellphone).build()), UserCode.Message.CELLPHONE_ALREADY_EXISTS);

        //校验验证码
        captchaService.validityByCellphone(cellphone, captcha);

        User user = SecurityContextHolder.getCurrentUser();
        user.setCellphone(cellphone);
        userService.update(user);
        return Response.success();
    }

//    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_UPDATE + "')")
//    @ApiOperation(value = "邮箱", notes = "邮箱", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "email/{email}/{captcha}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response email(@PathVariable String email, @PathVariable String captcha) {
//        //正则校验
//        ApiAssert.state(Pattern.matches("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", email), CommonCode.Message.EMAIL_FORMAT_ERROR);
//
//        //检查邮箱是否存在
//        ApiAssert.state(!userQueryService.exists(UserQO.builder().email(email).build()), UserCode.Message.EMAIL_ALREADY_EXISTS);
//
//        //校验验证码
//        captchaService.validityByEmail(email, captcha);
//
//        User user = SecurityContextHolder.getCurrentUser();
//        user.setEmail(email);
//        userService.update(user);
//        return Response.success();
//    }

    //    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_UPDATE + "')")
    @ApiOperation(value = "邮箱", notes = "邮箱", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "email/{email:.+}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response email(@PathVariable String email) {
        //正则校验
        ApiAssert.state(Pattern.matches("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", email), CommonCode.Message.EMAIL_FORMAT_ERROR);

        //检查邮箱是否存在
        ApiAssert.state(!userQueryService.exists(UserQO.builder().email(email).build()), UserCode.Message.EMAIL_ALREADY_EXISTS);

        User user = SecurityContextHolder.getCurrentUser();
        user.setEmail(email);
        userService.update(user);
        return Response.success();
    }

    //    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_UPDATE + "')")
    @ApiOperation(value = "nickname", notes = "昵称", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "nickname/{nickname}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response nickname(@PathVariable String nickname) {
        //正则校验
//        ApiAssert.state(Pattern.matches("^1[0-9]{10}$", cellphone), 100000, "请输入11位有效手机号码");

        //检查手机号码是否存在
//        ApiAssert.state(userQueryService.exists(UserQO.builder().ni(cellphone).build()), CommonCode.Message.USER_CELLPHONE_ALREADY_EXISTED);

        //校验验证码
//        captchaService.validityByCellphone(cellphone, captcha);
//
//        User user = SecurityContextHolder.getCurrentUser();
//        user.setCellphone(cellphone);
//        userService.update(user);
        return Response.success();
    }

}
