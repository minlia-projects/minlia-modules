package com.minlia.modules.qcloud.start.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.qcloud.start.service.QcloudAuthService;
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
@Api(tags = "Qloud", description = "腾讯云")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "tencent/cloud")
public class QcloudAuthEndpoint {

    @Autowired
    private QcloudAuthService qcloudAuthService;

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "Access Token", notes = "获取Access token", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "access_token", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response getAccessToken() {
        return Response.success(qcloudAuthService.getAccessToken());
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "Api Sign Ticket", notes = "获取Api Ticket", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "api_sign_ticket", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response getApiSignTicket() {
        return Response.success(qcloudAuthService.getApiSignTicket());
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "Api Nonce Ticket", notes = "获取Api Ticket", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "api_nonce_ticket", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response getApiNonceTicket() {
        return Response.success(qcloudAuthService.getApiNonceTicket());
    }

}
