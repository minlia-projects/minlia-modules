package com.minlia.module.tencent.wechat.mp.config;

import com.minlia.module.tencent.wechat.mp.handler.*;
import lombok.Getter;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass(WxMpService.class)
public class WechatMpHandlerConfig {

    @Autowired
    @Getter
    private LocationHandler locationHandler;

    @Autowired
    @Getter
    private MenuHandler menuHandler;

    @Autowired
    @Getter
    private MsgHandler msgHandler;

    @Autowired
    @Getter
    protected LogHandler logHandler;

    @Autowired
    @Getter
    protected NullHandler nullHandler;

    @Autowired
    @Getter
    protected KfSessionHandler kfSessionHandler;

    @Autowired
    @Getter
    protected StoreCheckNotifyHandler storeCheckNotifyHandler;

    @Autowired
    @Getter
    private UnsubscribeHandler unsubscribeHandler;

    @Autowired
    @Getter
    private SubscribeHandler subscribeHandler;

    @Autowired
    @Getter
    private ScanHandler scanHandler;

    @Bean
    public WxMpMessageRouter router(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);
// 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(this.logHandler).next();

//        // 接收客服会话管理事件
//        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
//                .event(WxConsts.EVT_KF_CREATE_SESSION)
//                .handler(this.kfSessionHandler).end();

//        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
//                .event(WxConsts.EVT_KF_CLOSE_SESSION).handler(this.kfSessionHandler)
//                .end();

//        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
//                .event(WxConsts.EVT_KF_SWITCH_SESSION)
//                .handler(this.kfSessionHandler).end();
//
//        // 门店审核事件
//        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
//                .event(WxConsts.EVT_POI_CHECK_NOTIFY)
//                .handler(this.storeCheckNotifyHandler).end();

        // 自定义菜单事件
        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
                .event(WxConsts.BUTTON_CLICK).handler(this.getMenuHandler()).end();

        // 点击菜单连接事件
        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
                .event(WxConsts.BUTTON_VIEW).handler(this.nullHandler).end();

        // 关注事件
        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
                .event(WxConsts.EVT_SUBSCRIBE).handler(this.getSubscribeHandler())
                .end();

        // 取消关注事件
        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
                .event(WxConsts.EVT_UNSUBSCRIBE)
                .handler(this.getUnsubscribeHandler()).end();

        // 上报地理位置事件
        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
                .event(WxConsts.EVT_LOCATION).handler(this.getLocationHandler())
                .end();

        // 接收地理位置消息
        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_LOCATION)
                .handler(this.getLocationHandler()).end();

        // 扫码事件
        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
                .event(WxConsts.EVT_SCAN).handler(this.getScanHandler()).end();

        // 默认
        newRouter.rule().async(false).handler(this.getMsgHandler()).end();

        return newRouter;
    }

}
