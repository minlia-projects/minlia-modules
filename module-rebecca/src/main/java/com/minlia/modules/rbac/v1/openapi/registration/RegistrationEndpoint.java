package com.minlia.modules.rbac.v1.openapi.registration;

import com.minlia.cloud.body.AvailablitityResponseBody;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.rbac.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by will on 6/19/17.
 */
@RestController
@RequestMapping(value = ApiPrefix.API+"user")
@Api(tags = "Open Api", description = "开放接口")
@Slf4j
public class RegistrationEndpoint {

    @Autowired
    UserRegistrationService userRegistrationService;

    /**
     * 用户注册
     * @return
     */
    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "registration", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody registration(@Valid @RequestBody UserRegistrationRequestBody body ) {
        User userRegistered=userRegistrationService.registration(body);
        userRegistered.setPassword("******");
        return SuccessResponseBody.builder().payload(userRegistered).build();
    }

    /**
     * 用户注册
     * @return
     */
    @ApiOperation(value = "用户名有效性验证", notes = "用户名有效性验证", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "availablitity", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody availablitity(@Valid @RequestBody UserAvailablitityRequestBody body ) {
        Boolean available=userRegistrationService.availablitity(body);
        AvailablitityResponseBody responseBody=new AvailablitityResponseBody();
        int code=0;
        String message="NotAvailable";
        if(available){
            code=1;
            message="Available";
            responseBody.setAvailable(Boolean.TRUE);
        }else{
            responseBody.setAvailable(Boolean.FALSE);
        }

        responseBody.setMessage(message);
        return SuccessResponseBody.builder().code(code).payload(responseBody).build();
    }

}
