package com.minlia.modules.tencent.cloud.auth.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.tencent.cloud.auth.body.TcFaceIdRequestBody;
import com.minlia.modules.tencent.cloud.auth.service.TcAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by garen on 2018/4/19.
 */
@Api(tags = "Tencent Cloud Auth", description = "腾讯云认证")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "tencent/cloud/oauth2")
public class TcAuthEndpoint {

    @Autowired
    private TcAuthService tcAuthService;

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "Access Token", notes = "获取Access token", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "access_token", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody getAccessToken() {
        return SuccessResponseBody.builder().payload(tcAuthService.getAccessToken()).build();
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "Api Ticket", notes = "获取Api Ticket", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "api_ticket", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody getApiTicket() {
        return SuccessResponseBody.builder().payload(tcAuthService.getApiTicket()).build();
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "geth5faceid", notes = "获取h5faceid", httpMethod = "POST",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "geth5faceid", method = RequestMethod.POST,consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody geth5faceid(@Valid @RequestBody TcFaceIdRequestBody requestBody) {
        return tcAuthService.geth5faceid(requestBody);
    }

//    @PreAuthorize(value = "isAuthenticated()")
//    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody update(@Valid @RequestBody AttachmentUpdateRequestBody requestBody) {
//        Attachment attachment = mapper.map(requestBody,Attachment.class);
//        return SuccessResponseBody.builder().payload(attachmentService.update(attachment)).build();
//    }

}
