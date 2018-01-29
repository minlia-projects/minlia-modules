package com.minlia.module.wechat.mp.event;

import com.minlia.cloud.holder.ContextHolder;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.springframework.context.ApplicationEvent;

/**
 * Created by garen on 2017/11/27.
 */
public class WechatScanEvent extends ApplicationEvent{

    public WechatScanEvent(Object source) {
        super(source);
    }

    /**
     * @param wxMessage
     */
    public static void onScan(WxMpXmlMessage wxMessage){
        ContextHolder.getContext().publishEvent(new WechatScanEvent(wxMessage));
    }

}
