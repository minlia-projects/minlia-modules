package com.minlia.module.wechat.mp.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


/**
 * 微信后台
 * Created by laidu on 2017/5/4.
 */
@Slf4j
@RestController
@Api(tags = "Wechat MP", description = "微信公众号")
@RequestMapping(ApiPrefix.API + "wechat")
public class WechatMpEndpoint {

    public static final Integer EXPIRE_SECONDS = 2592000;

    @Autowired(required = false)
    private WxMpService wxService;

    @Autowired(required = false)
    private WxMpMessageRouter router;

    /**
     * 获取微信临时二维码
     *
     * @param scene
     * @param seconds
     * @return
     * @throws WxErrorException
     */
    @RequestMapping(value = "tempqrcode/{scene}/{seconds}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "微信临时二维码", notes = "微信临时二维码", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody qrcode(@PathVariable String scene,@PathVariable Integer seconds) throws WxErrorException {
        WxMpQrCodeTicket ticket = wxService.getQrcodeService().qrCodeCreateTmpTicket(scene,seconds);
        String qrcode = wxService.getQrcodeService().qrCodePictureUrl(ticket.getTicket(), true);
        log.debug("QRCODE {}", qrcode);
        return SuccessResponseBody.builder().message(qrcode).build();
    }

    /**
     * 微信临时二维码（判断类型）
     *
     * @param parameter
     * @param prefixType
     * @return
     * @throws WxErrorException
     */
    @RequestMapping(value = "tempqrcode/{parameter}/{prefixType}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "微信临时二维码（类型）", notes = "微信临时二维码（类型）", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody qrcode(@PathVariable String parameter, @PathVariable String prefixType) throws WxErrorException {
        WxMpQrCodeTicket ticket = wxService.getQrcodeService().qrCodeCreateLastTicket(prefixType + parameter);
        String qrcode = wxService.getQrcodeService().qrCodePictureUrl(ticket.getTicket(), true);
        log.debug("QRCODE {}", qrcode);
        return SuccessResponseBody.builder().message(qrcode).build();
    }

    /**
     * 微信后台配置token校验
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @GetMapping(produces = "text/plain;charset=utf-8")
    public String authGet(
            @RequestParam(name = "signature", required = false) String signature,
            @RequestParam(name = "timestamp", required = false) String timestamp,
            @RequestParam(name = "nonce", required = false) String nonce,
            @RequestParam(name = "echostr", required = false) String echostr) {

        log.info("接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        if (this.wxService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }

        return "非法请求";
    }

    /**
     * 微信回调处理
     *
     * @param requestBody
     * @param signature
     * @param timestamp
     * @param nonce
     * @param encType
     * @param msgSignature
     * @return
     */
    @PostMapping(produces = "application/xml; charset=UTF-8")
    public String callBack(@RequestBody String requestBody,
                           @RequestParam("signature") String signature,
                           @RequestParam("timestamp") String timestamp,
                           @RequestParam("nonce") String nonce,
                           @RequestParam(name = "encrypt_type", required = false) String encType,
                           @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        log.info("接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}]," + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                signature, encType, msgSignature, timestamp, nonce, requestBody);

        if (!this.wxService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        String out = null;
        if (encType == null) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toXml();
        } else if ("aes".equals(encType)) {
            // aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(
                    requestBody, this.wxService.getWxMpConfigStorage(), timestamp, nonce, msgSignature);
            log.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toEncryptedXml(this.wxService.getWxMpConfigStorage());
        }

        log.debug("\n组装回复信息：{}", out);
        return out;
    }

    private WxMpXmlOutMessage route(WxMpXmlMessage message) {
        try {
            return this.router.route(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
