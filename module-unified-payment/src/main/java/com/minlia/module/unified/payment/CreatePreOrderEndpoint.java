package com.minlia.module.unified.payment;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.unified.payment.body.CreatePreOrderRequestBody;
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
@Api(tags = "payment",value = "支付")
public class CreatePreOrderEndpoint {

  @ApiOperation(value = "create", notes = "create", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @PostMapping(value = "create",consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody create(@Valid @RequestBody CreatePreOrderRequestBody body) {
    CreatePreOrderService service = ContextHolder.getContext().getBean(body.getGateway()+"CreatePreOrderService",CreatePreOrderService.class);
    if(null==service){
      throw  new RuntimeException("Unsupported gateway");
    }
    return service.createPreOrder(body);
  }

}
