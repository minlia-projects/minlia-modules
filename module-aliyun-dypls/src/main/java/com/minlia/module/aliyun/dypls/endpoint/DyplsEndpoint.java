package com.minlia.module.aliyun.dypls.endpoint;

import com.aliyuncs.dyplsapi.model.v20170525.BindAxbRequest;
import com.aliyuncs.dyplsapi.model.v20170525.BindAxnExtensionRequest;
import com.aliyuncs.dyplsapi.model.v20170525.UpdateSubscriptionRequest;
import com.aliyuncs.exceptions.ClientException;
import com.minlia.cloud.body.Response;
import com.minlia.module.aliyun.dypls.body.BindAxnRequestBody;
import com.minlia.module.aliyun.dypls.service.DyplsBindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by garen on 2018/5/18.
 */
@Api(tags = "Aliyun Dypls", description = "隐私号码")
@RestController
@RequestMapping(value = "/api/dypls")
public class DyplsEndpoint {

    @Autowired
    private DyplsBindService dyplsBindService;

    public Response bindAxb(BindAxbRequest request) throws ClientException {
        return Response.failure("开发中");
    }

    @ApiOperation(value = "Bind Axn", notes = "Bind Axn", httpMethod = "POST")
    @PostMapping(value = "bindaxn", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response bindAxn(@RequestBody BindAxnRequestBody body) throws ClientException {
        return dyplsBindService.bindAxn(body);
    }

    public Response bindAxnExtension(BindAxnExtensionRequest request) throws ClientException {
        return Response.failure("开发中");
    }

    public Response updateSubscription(UpdateSubscriptionRequest request) throws ClientException {
        return Response.failure("开发中");
    }

    @ApiOperation(value = "unbind", notes = "unbind", httpMethod = "POST")
    @PostMapping(value = "unbind/{subsId}/{secretNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response unbind(@PathVariable String subsId, @PathVariable String secretNo) throws ClientException {
        return dyplsBindService.unbind(subsId,secretNo);
    }

}
