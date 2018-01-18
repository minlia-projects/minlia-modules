package com.minlia.module.tencent.wechat.mp.event;

import com.minlia.cloud.holder.ContextHolder;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.springframework.context.ApplicationEvent;

/**
 * Created by garen on 2017/11/27.
 */
public class WechatSubscribeEvent extends ApplicationEvent{

    public WechatSubscribeEvent(Object source) {
        super(source);
    }

    /**
     * 关注事件
     * @param wxMessage
     */
    public static void onSubscribe(WxMpXmlMessage wxMessage){
        ContextHolder.getContext().publishEvent(new WechatSubscribeEvent(wxMessage));
    }

}
