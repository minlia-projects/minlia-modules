package com.minlia.module.unified.payment;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.unified.payment.bean.CreatePreOrderRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by will on 9/14/17.
 * 使用示例
 */
@RestController
@RequestMapping(value = "/api/v1/unified/payment")
@Api(tags = "System Unified Payment", value = "统一支付")
public class CreatePreOrderEndpoint {

    @ApiOperation(value = "create", notes = "create", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "create", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response create(@Valid @RequestBody CreatePreOrderRequest request) {
        CreatePreOrderService service = ContextHolder.getContext().getBean(request.getChannel().getPlatform() + "CreatePreOrderService", CreatePreOrderService.class);
        if (null == service) {
            throw new RuntimeException("Unsupported gateway");
        }
        return service.createPreOrder(request);
    }

}