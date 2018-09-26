package com.minlia.module.pooul.endpoint;

import com.auth0.jwt.interfaces.Claim;
import com.minlia.module.pooul.bean.domain.PooulOrderDO;
import com.minlia.module.pooul.bean.dto.PooulPayNotifyDTO;
import com.minlia.module.pooul.bean.dto.PooulPayNotifyData;
import com.minlia.module.pooul.bean.qo.PooulOrderQO;
import com.minlia.module.pooul.contract.PooulContracts;
import com.minlia.module.pooul.enumeration.PayStatusEnum;
import com.minlia.module.pooul.event.PooulEventPublisher;
import com.minlia.module.pooul.mapper.PooulOrderMapper;
import com.minlia.module.pooul.util.PooulToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * 普尔支付通知
 * Created by garen on 2018/7/23.
 */
@Api(tags = "Pooul Pay Notify", description = "普尔支付通知")
@RestController
@RequestMapping(value = "api/open/pooul/notify")
@Slf4j
public class PooulPayNotifyEndpoint {

    @Autowired
    private PooulOrderMapper pooulOrderMapper;

    @ApiOperation(value = "通知", notes = "通知", httpMethod = "POST", consumes = MediaType.TEXT_PLAIN_VALUE)
    @PostMapping(value = "pay", produces = {MediaType.TEXT_PLAIN_VALUE})
    public String notify(String token, HttpServletRequest request, HttpServletResponse response) {
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            while (null != (line = reader.readLine())) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("普尔支付通知:",e);
            //TODO 持久化错误日志
            return "success";
        } finally {
        }

        //公钥验证数据是否合法、返回自定义数据/业务数据
        Map<String,Claim> claims = PooulToken.getClaims(sb.toString());
        PooulPayNotifyDTO notifyDTO = PooulPayNotifyDTO.builder()
                .code(claims.get(PooulContracts.CODE).asInt())
                .nonceStr(claims.get(PooulContracts.NONCE_STR).asString())
                .data(claims.get(PooulContracts.DATA).as(PooulPayNotifyData.class))
                .build();

        //持久化通知记录：历史记录 TODO


        if (notifyDTO.isSuccess() && notifyDTO.getData().isSuccess()) {
            //成功 、处理后续业务
            //更新状态为支付成功
            PooulOrderDO pooulOrderDO = pooulOrderMapper.queryOne(PooulOrderQO.builder().mchTradeId(notifyDTO.getData().getMchTradeId()).build());
            pooulOrderDO.setPayStatus(PayStatusEnum.PAID);
            pooulOrderMapper.update(pooulOrderDO);
        } else {
            //失败、处理后续业务 TODO

        }

        //发布通知事件
        PooulEventPublisher.onPaid(notifyDTO);

//        if (notifyResponseBody.isSuccess() && notifyResponseBody.getData().isSuccess()) {
//            //成功 、处理后续业务
//            PooulEventPublisher.onPaySuccess(notifyResponseBody);
//        } else {
//            //失败、处理后续业务
//            PooulEventPublisher.onPayFailure(notifyResponseBody);
//        }
        return "success";
    }

}
