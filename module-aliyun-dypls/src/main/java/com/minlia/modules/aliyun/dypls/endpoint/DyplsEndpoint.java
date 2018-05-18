package com.minlia.modules.aliyun.dypls.endpoint;

import com.aliyuncs.dyplsapi.model.v20170525.BindAxbRequest;
import com.aliyuncs.dyplsapi.model.v20170525.BindAxnExtensionRequest;
import com.aliyuncs.dyplsapi.model.v20170525.BindAxnRequest;
import com.aliyuncs.dyplsapi.model.v20170525.UpdateSubscriptionRequest;
import com.aliyuncs.exceptions.ClientException;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.FailureResponseBody;
import com.minlia.modules.aliyun.dypls.body.BindAxnRequestBody;
import com.minlia.modules.aliyun.dypls.service.DyplsBindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by garen on 2018/5/18.
 */
@RestController
@RequestMapping(value = "/api/dypls")
@Api(tags = "Aliyun Dypls", description = "号码隐私")
public class DyplsEndpoint {

    @Autowired
    private DyplsBindService dyplsBindService;

    public StatefulBody bindAxb(BindAxbRequest request) throws ClientException {
        return FailureResponseBody.builder().message("开发中").build();
    }

    @ApiOperation(value = "Bind Axn", notes = "Bind Axn", httpMethod = "POST")
    @PostMapping(value = "bindaxn", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody bindAxn(@RequestBody BindAxnRequestBody body) throws ClientException {
        return FailureResponseBody.builder().payload(dyplsBindService.bindAxn(body)).build();
    }

    public StatefulBody bindAxnExtension(BindAxnExtensionRequest request) throws ClientException {
        return FailureResponseBody.builder().message("开发中").build();
    }

    public StatefulBody updateSubscription(UpdateSubscriptionRequest request) throws ClientException {
        return FailureResponseBody.builder().message("开发中").build();
    }

    @ApiOperation(value = "unbind", notes = "unbind", httpMethod = "POST")
    @PostMapping(value = "unbind/{subsId}/{secretNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody unbind(@PathVariable String subsId, @PathVariable String secretNo) throws ClientException {
        return FailureResponseBody.builder().payload(dyplsBindService.unbind(subsId,secretNo)).build();
    }

}
