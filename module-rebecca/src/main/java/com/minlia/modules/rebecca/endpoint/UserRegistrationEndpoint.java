package com.minlia.modules.rebecca.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.OperationTypeEnum;
import com.minlia.modules.rebecca.constant.UserCode;
import com.minlia.modules.rebecca.service.UserQueryService;
import com.minlia.modules.rebecca.bean.qo.UserQO;
import com.minlia.modules.rebecca.bean.domain.User;
import com.minlia.modules.rebecca.bean.to.UserAvailablitityTO;
import com.minlia.modules.rebecca.bean.to.UserRegistrationTO;
import com.minlia.modules.rebecca.service.UserRegistrationService;
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
@RequestMapping(value = ApiPrefix.API + "user/registration")
public class UserRegistrationEndpoint {

    @Autowired
    private UserQueryService userQueryService;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @AuditLog(value = "user registration", type = OperationTypeEnum.CREATE)
    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response registration(@Valid @RequestBody UserRegistrationTO to) {
        User userRegistered = userRegistrationService.registration(to);
        return Response.success(userRegistered);
    }

    @AuditLog(value = "user availability check", type = OperationTypeEnum.INFO)
    @ApiOperation(value = "用户名有效性验证", notes = "用户名有效性验证", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "availabilitity", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response availability(@Valid @RequestBody UserAvailablitityTO body) {
        return userRegistrationService.availablitity(body);
    }

    @AuditLog(value = "user referral existence check", type = OperationTypeEnum.INFO)
    @ApiOperation(value = "推荐人是否存在", notes = "推荐人是否有效", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "verifyReferral", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response invitationCode(@RequestParam String referral) {
        if (userQueryService.exists(UserQO.builder().referral(referral).build())) {
            return Response.success();
        } else {
            return Response.failure(UserCode.Message.REFERRAL_NOT_EXISTS);
        }
    }

}
