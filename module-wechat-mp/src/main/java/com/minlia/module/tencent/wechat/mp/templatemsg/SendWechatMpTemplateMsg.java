package com.minlia.module.tencent.wechat.mp.templatemsg;

import cn.binarywang.wx.miniapp.config.WxMaConfig;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.FailureResponseBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.holder.ContextHolder;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.bible.entity.BibleItem;
import com.minlia.module.bible.service.BibleItemService;
import com.minlia.module.tencent.miniapp.body.WechatOpenAccountQueryBody;
import com.minlia.module.tencent.miniapp.domain.WechatOpenAccount;
import com.minlia.module.tencent.miniapp.enumeration.WechatOpenidType;
import com.minlia.module.tencent.miniapp.service.WechatOpenAccountService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage.MiniProgram;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Calvin on 2017/8/11.
 */
@Slf4j
public class SendWechatMpTemplateMsg {

    /**
     * 微信公众号模板CODE
     */
    public static final String WECHAT_PUBLIC_TEMPLATE_MSG="WECHAT_PUBLIC_TEMPLATE_MSG";

    /**
     * 加载实体Bean
     * @param var1
     * @param <T>
     * @return
     */
    private static <T> T getBeanByContext(Class<T> var1){
        return ContextHolder.getContext().getBean(var1);
    }

    /**
     * 获取小程序路径 / 获取Wechat_mp templateID
     * @param templateId
     * @return
     */
    private MiniProgram builderMiniProgram(String appId, String templateId,List<Object> pathParams){
        BibleItem bibleItem = getBeanByContext(BibleItemService.class).queryByParentCodeAndCode(WECHAT_PUBLIC_TEMPLATE_MSG, templateId);
        ApiPreconditions.is(null == bibleItem, ApiCode.NOT_FOUND, String.format("小程序路径%s不能为空",templateId));
        return new MiniProgram(appId, String.format(bibleItem.getAttribute1(),pathParams));
    }

    /**
     * 通过校验后，获取openId
     * @return
     */
    private String getOpenId(Long userId){
        WechatOpenAccount wechatOpenAccount = getBeanByContext(WechatOpenAccountService.class).findOne(WechatOpenAccountQueryBody.builder().userId(userId).openType(WechatOpenidType.PUBLIC).isSubscribe(true).build());
        return null == wechatOpenAccount ? null : wechatOpenAccount.getOpenId();
    }

    /**
     * @param userId    用户ID 获取openId
     * @param templateId   模版ID
     * @param templateDesc  模版描述
     * @param keyValue  内容
     * @return
     */
    public StatefulBody send(Long userId, String templateId, String templateDesc, List<Object> pathParams, String... keyValue) {
        String openId = this.getOpenId(userId);
        if (StringUtils.isEmpty(openId)) {
            return FailureResponseBody.builder().message(String.format("公众号通知失败- %s %s OpenId不能为空",templateDesc, userId)).build();
        } else {
            return this.send(getOpenId(userId),templateId,templateDesc,pathParams,keyValue);
        }
    }

    public StatefulBody send(String openId, String templateId, String templateDesc, List<Object> pathParams, String... keyValue) {
        try {
            List<WxMpTemplateData> data = new ArrayList<WxMpTemplateData>();
            String keyword;
            String color = "#173177";
            for(int i=0; i<keyValue.length; i++){
                if(i==0){
                    keyword = "first";
                }else if(i==keyValue.length-1){
                    keyword = "remark";
                    color = "#FF3333";
                }else{
                    keyword = "keyword"+i;
                }
                data.add(new WxMpTemplateData(keyword, keyValue[i], color));
            }

            WxMpService wxMpService = ContextHolder.getContext().getBean(WxMpService.class);
            WxMaConfig wxMaConfig = ContextHolder.getContext().getBean(WxMaConfig.class);
            BibleItem bibleItem = getBeanByContext(BibleItemService.class).queryByParentCodeAndCode(WECHAT_PUBLIC_TEMPLATE_MSG, templateId);
            ApiPreconditions.is(null == bibleItem, ApiCode.NOT_FOUND, String.format("小程序路径%s不能为空",templateId));

            WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
            wxMpTemplateMessage.setData(data);
            wxMpTemplateMessage.setToUser(openId);
            wxMpTemplateMessage.setTemplateId(bibleItem.getLabel());
            wxMpTemplateMessage.setMiniProgram(new MiniProgram(wxMaConfig.getAppid(), String.format(bibleItem.getAttribute1(),pathParams.toArray())));

            //发送模板消息
            String templateMsg = wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
            return SuccessResponseBody.builder().message(templateMsg).build();
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error(String.format("公众号通知失败-%s: %s",templateDesc, e.getError()));
            return FailureResponseBody.builder().code(e.getError().getErrorCode()).message(templateDesc + e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(String.format("公众号通知失败-%s: %s",templateDesc, e.getMessage()));
            return FailureResponseBody.builder().message(templateDesc + e.getMessage()).build();
        }
    }

}

