package com.minlia.modules.qcloud.faceid.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.qcloud.faceid.bean.to.QcloudFaceIdTO;
import com.minlia.modules.qcloud.faceid.bean.dto.QcloudFaceIdResultDTO;
import com.minlia.modules.qcloud.faceid.service.QcloudFaceidService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by garen on 2018/4/19.
 */
@Api(tags = "QCloud Faceid", description = "腾讯云面部认证")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "tencent/cloud/oauth2")
public class QcloudFaceidEndpoint {

    @Autowired
    private QcloudFaceidService tcAuthService;

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "geth5faceid", notes = "获取h5faceid", httpMethod = "POST",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "geth5faceid", method = RequestMethod.POST,consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response geth5faceid(@Valid @RequestBody QcloudFaceIdTO to) {
        return tcAuthService.geth5faceid(to);
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "geth5faceidResult", notes = "获取h5faceid", httpMethod = "POST",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "geth5faceidresult/{orderNo}", method = RequestMethod.POST,consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public QcloudFaceIdResultDTO geth5faceidResult(@PathVariable String orderNo) {
        return tcAuthService.getH5faceidResult(orderNo);
    }

}
