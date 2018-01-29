package com.minlia.modules.rbac.openapi.registration.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.FailureResponseBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.backend.user.service.UserQueryService;
import com.minlia.modules.rbac.openapi.registration.body.UserAvailablitityRequestBody;
import com.minlia.modules.rbac.openapi.registration.body.UserRegistrationRequestBody;
import com.minlia.modules.rbac.openapi.registration.service.UserRegistrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by will on 6/19/17.
 */
@RestController
@RequestMapping(value = ApiPrefix.API+"user/registration")
@Api(tags = "System Open Registration", description = "注册")
@Slf4j
public class UserRegistrationEndpoint {

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private UserQueryService userQueryService;

    @Autowired
    private UserRegistrationService userRegistrationService;

    /**
     * 用户注册
     * @return
     */
    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody registration(@Valid @RequestBody UserRegistrationRequestBody body ) {
        //验证码校验:用户名必须时手机号码 TODO
        captchaService.validity(body.getUsername(),body.getCode());

        User userRegistered = userRegistrationService.registration(body);
        userRegistered.setPassword("******");
        return SuccessResponseBody.builder().payload(userRegistered).build();
    }

    @ApiOperation(value = "用户名有效性验证", notes = "用户名有效性验证", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "availablitity", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody availablitity(@Valid @RequestBody UserAvailablitityRequestBody body ) {
        return userRegistrationService.availablitity(body);
    }

    @ApiOperation(value = "推荐人是否存在", notes = "推荐人是否有效", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "verifyreferee", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody InvitationCode(@RequestParam String referee) {
        if (userQueryService.exists(referee))
            return SuccessResponseBody.builder().payload(true).build();
        else
            return FailureResponseBody.builder().payload(false).message("推荐人不存在").build();
    }

}
