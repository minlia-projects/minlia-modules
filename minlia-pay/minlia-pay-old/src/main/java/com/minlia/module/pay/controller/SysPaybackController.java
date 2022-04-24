package com.minlia.module.pay.controller;

import com.egzosn.pay.common.api.PayMessageInterceptor;
import com.egzosn.pay.spring.boot.core.PayServiceManager;
import com.egzosn.pay.web.support.HttpRequestNoticeParams;
import com.minlia.cloud.constant.ApiPrefix;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>
 * 支付回调
 * </p>
 *
 * @author garen
 * @since 2020-08-18
 */
@Slf4j
@Api(tags = "System Pay Back", description = "支付-回调")
@Controller
@RequestMapping(value = ApiPrefix.API + "payback")
@RequiredArgsConstructor
public class SysPaybackController {

    private final PayServiceManager payServiceManager;

    /**
     * 支付回调地址
     * 拦截器相关增加， 详情查看{@link com.egzosn.pay.common.api.PayService#addPayMessageInterceptor(PayMessageInterceptor)}
     * <p>
     * 业务处理在对应的PayMessageHandler里面处理，在哪里设置PayMessageHandler，详情查看{@link com.egzosn.pay.common.api.PayService#setPayMessageHandler(com.egzosn.pay.common.api.PayMessageHandler)}
     * </p>
     * 如果未设置 {@link com.egzosn.pay.common.api.PayMessageHandler} 那么会使用默认的 {@link com.egzosn.pay.common.api.DefaultPayMessageHandler}
     *
     * @param request   请求
     * @param detailsId 列表id
     * @return 支付是否成功
     * @throws IOException IOException
     */
    @PostMapping(value = "{detailsId}")
    public String aliPayBack(HttpServletRequest request, @PathVariable String detailsId) {
        log.info("支付回调开始=============================={}", detailsId);
        return payServiceManager.payBack(detailsId, new HttpRequestNoticeParams(request));
    }

//    @PostMapping(value = "{orderNo}/{tradeNo}")
//    public Response payBack(@PathVariable("orderNo") String orderNo, @PathVariable("tradeNo") String tradeNo) {
//        sysPayOrderService.callback(orderNo, tradeNo);
//        return Response.success();
//    }

}