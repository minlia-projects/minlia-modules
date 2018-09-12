package com.minlia.modules.rbac.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.FailureResponseBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.rbac.backend.user.body.UserQueryRequestBody;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.backend.user.service.UserQueryService;
import com.minlia.modules.rbac.bean.to.UserAvailablitityRequestBody;
import com.minlia.modules.rbac.bean.to.UserRegistrationRequestBody;
import com.minlia.modules.rbac.service.UserRegistrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by will on 6/19/17.
 */
@Api(tags = "System Registration", description = "注册")
@RestController
@RequestMapping(value = ApiPrefix.API+"user/registration")
public class UserRegistrationEndpoint {

    @Autowired
    private UserQueryService userQueryService;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody registration(@Valid @RequestBody UserRegistrationRequestBody body ) {
        User userRegistered = userRegistrationService.registration(body);
        return SuccessResponseBody.builder().payload(userRegistered).build();
    }

    @ApiOperation(value = "用户名有效性验证", notes = "用户名有效性验证", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "availablitity", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody availablitity(@Valid @RequestBody UserAvailablitityRequestBody body ) {
        return userRegistrationService.availablitity(body);
    }

    @ApiOperation(value = "推荐人是否存在", notes = "推荐人是否有效", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "verifyReferral", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody invitationCode(@RequestParam String referral) {
        if (userQueryService.exists(UserQueryRequestBody.builder().referral(referral).build())) {
            return SuccessResponseBody.builder().payload(true).build();
        }
        else {
            return FailureResponseBody.builder().payload(false).message("推荐人不存在").build();
        }
    }

}
