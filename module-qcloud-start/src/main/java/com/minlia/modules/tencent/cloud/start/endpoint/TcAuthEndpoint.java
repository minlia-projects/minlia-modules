package com.minlia.modules.tencent.cloud.start.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.tencent.cloud.start.service.TcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by garen on 2018/4/19.
 */
@Api(tags = "Tencent Cloud", description = "腾讯云")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "tencent/cloud")
public class TcAuthEndpoint {

    @Autowired
    private TcService tcService;

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "Access Token", notes = "获取Access token", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "access_token", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody getAccessToken() {
        return SuccessResponseBody.builder().payload(tcService.getAccessToken()).build();
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "Api Sign Ticket", notes = "获取Api Ticket", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "api_sign_ticket", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody getApiSignTicket() {
        return SuccessResponseBody.builder().payload(tcService.getApiSignTicket()).build();
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "Api Nonce Ticket", notes = "获取Api Ticket", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "api_nonce_ticket", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody getApiNonceTicket() {
        return SuccessResponseBody.builder().payload(tcService.getApiNonceTicket()).build();
    }

}
