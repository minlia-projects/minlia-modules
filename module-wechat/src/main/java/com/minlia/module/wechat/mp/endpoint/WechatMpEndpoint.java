package com.minlia.module.wechat.mp.endpoint;

import com.google.gson.Gson;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.constant.ApiPrefix;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;
import me.chanjar.weixin.mp.api.WxMpMaterialService;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.enums.WxMpApiUrl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


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
    private WxMpService wxMpService;

    @Autowired(required = false)
    private WxMpMessageRouter router;

    @ApiOperation(value = "获取素材列表", notes = "获取素材列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "material/{type}/{offset}/{count}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response material(@PathVariable String type, @PathVariable int offset, @PathVariable int count) throws WxErrorException {
//        WxMpMaterialFileBatchGetResult result = wxMpService.getMaterialService().materialFileBatchGet(type, offset, count);

        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("offset", offset);
        params.put("count", count);
<<<<<<< HEAD
//        String responseText = this.wxMpService.post(WxMpMaterialService.MATERIAL_BATCHGET_URL, WxGsonBuilder.create().toJson(params));
        String responseText = this.wxMpService.post(WxMpApiUrl.Material.MATERIAL_BATCHGET_URL, WxGsonBuilder.create().toJson(params));
=======
        String url = WxMpApiUrl.Material.MATERIAL_BATCHGET_URL.getUrl(this.wxMpService.getWxMpConfigStorage());
        String responseText = this.wxMpService.post(url, WxGsonBuilder.create().toJson(params));
>>>>>>> dev/garen
        WxError wxError = WxError.fromJson(responseText);
        if (wxError.getErrorCode() != 0) {
            throw new WxErrorException(wxError);
        }
        return Response.success(new Gson().fromJson(responseText, HashMap.class));
    }

    @ApiOperation(value = "临时二维码", notes = "临时二维码", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "tempqrcode/{scene}/{seconds}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response qrcode(@PathVariable String scene, @PathVariable Integer seconds) throws WxErrorException {
        WxMpQrCodeTicket ticket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(scene, seconds);
        String qrcode = wxMpService.getQrcodeService().qrCodePictureUrl(ticket.getTicket(), true);
        log.debug("QRCODE {}", qrcode);
        return Response.success(SystemCode.Message.SUCCESS, qrcode);
    }

    @ApiOperation(value = "永久二维码", notes = "永久二维码", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "lastqrcode/{scene}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response qrcode(@PathVariable String scene) throws WxErrorException {
        WxMpQrCodeTicket ticket = wxMpService.getQrcodeService().qrCodeCreateLastTicket(scene);
        String qrcode = wxMpService.getQrcodeService().qrCodePictureUrl(ticket.getTicket(), true);
        log.debug("QRCODE {}", qrcode);
        return Response.success(SystemCode.Message.SUCCESS, qrcode);
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

        if (this.wxMpService.checkSignature(timestamp, nonce, signature)) {
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

        if (!this.wxMpService.checkSignature(timestamp, nonce, signature)) {
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
                    requestBody, this.wxMpService.getWxMpConfigStorage(), timestamp, nonce, msgSignature);
            log.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toEncryptedXml(this.wxMpService.getWxMpConfigStorage());
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
