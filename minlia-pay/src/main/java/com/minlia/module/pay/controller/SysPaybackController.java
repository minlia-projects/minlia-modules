package com.minlia.module.pay.controller;

import com.egzosn.pay.common.api.PayMessageInterceptor;
import com.egzosn.pay.spring.boot.core.MerchantPayServiceManager;
import com.minlia.cloud.constant.ApiPrefix;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
@Api(tags = "Hsjs Pay Back", description = "支付-回调")
@Controller
@RequestMapping(value = ApiPrefix.OPEN + "/pay/back")
public class SysPaybackController {

    private final MerchantPayServiceManager merchantPayServiceManager;

    public SysPaybackController(MerchantPayServiceManager merchantPayServiceManager) {
        this.merchantPayServiceManager = merchantPayServiceManager;
    }

//    /**
//     * 支付回调地址
//     * 拦截器相关增加， 详情查看{@link com.egzosn.pay.common.api.PayService#addPayMessageInterceptor(PayMessageInterceptor)}
//     * <p>
//     * 业务处理在对应的PayMessageHandler里面处理，在哪里设置PayMessageHandler，详情查看{@link com.egzosn.pay.common.api.PayService#setPayMessageHandler(com.egzosn.pay.common.api.PayMessageHandler)}
//     * </p>
//     * 如果未设置 {@link com.egzosn.pay.common.api.PayMessageHandler} 那么会使用默认的 {@link com.egzosn.pay.common.api.DefaultPayMessageHandler}
//     *
//     * @param request   请求
//     * @param detailsId 列表id
//     * @return 支付是否成功
//     * @throws IOException IOException
//     */
//    @PostMapping(value = "alipay{detailsId}")
//    public String payBack(HttpServletRequest request, @PathVariable String detailsId) throws IOException {
//        log.info("支付宝支付回调开始=============================={}", detailsId);
//        log.info("支付宝支付回调开始=============================={}", request.getParameterMap());
//        return merchantPayServiceManager.payBack(detailsId, request.getParameterMap(), request.getInputStream());
//    }

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
    @RequestMapping(value = "alipay{detailsId}")
    public String aliPayBack(HttpServletRequest request, @PathVariable String detailsId) throws IOException {
        log.info("支付宝支付回调开始=============================={}", detailsId);
        return merchantPayServiceManager.payBack(detailsId, request.getParameterMap(), request.getInputStream());
    }

    @RequestMapping(value = "wechat{detailsId}")
    public String wxPayBack(HttpServletRequest request, @PathVariable String detailsId) throws IOException {
        log.info("微信支付回调开始=============================={}", detailsId);
        return merchantPayServiceManager.payBack(detailsId, request.getParameterMap(), request.getInputStream());
    }

    @RequestMapping(value = "paypal{detailsId}")
    public String paypalPayBack(HttpServletRequest request, @PathVariable String detailsId) throws IOException {
        log.info("Paypal支付回调开始=============================={}", detailsId);
        return merchantPayServiceManager.payBack(detailsId, request.getParameterMap(), request.getInputStream());
    }

}