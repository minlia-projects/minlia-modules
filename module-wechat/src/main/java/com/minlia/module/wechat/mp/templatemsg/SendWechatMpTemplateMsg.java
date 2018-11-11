package com.minlia.module.wechat.mp.templatemsg;

import cn.binarywang.wx.miniapp.config.WxMaConfig;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.holder.ContextHolder;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.bible.bean.qo.BibleItemQO;
import com.minlia.module.bible.bean.domain.BibleItem;
import com.minlia.module.bible.service.BibleItemService;
import com.minlia.module.wechat.ma.bean.WechatOpenAccountQueryBody;
import com.minlia.module.wechat.ma.constant.WechatMaCode;
import com.minlia.module.wechat.ma.entity.WechatOpenAccount;
import com.minlia.module.wechat.ma.enumeration.WechatOpenidType;
import com.minlia.module.wechat.ma.service.WechatOpenAccountService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage.MiniProgram;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin
 * @date 2017/8/11
 */
@Slf4j
public class SendWechatMpTemplateMsg {

    /**
     * 微信公众号模板CODE
     */
    public static final String WECHAT_MP_TEMPLATE="WECHAT_MP_TEMPLATE";

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
    @Deprecated
    private MiniProgram builderMiniProgram(String appId, String templateId,List<Object> pathParams){
        BibleItem bibleItem = getBeanByContext(BibleItemService.class).queryOne(BibleItemQO.builder().parentCode(WECHAT_MP_TEMPLATE).code(templateId).build());
        ApiAssert.notNull(bibleItem, WechatMaCode.Message.MA_PATH_NOT_NULL, templateId);
        return new MiniProgram(appId, String.format(bibleItem.getAttribute1(),pathParams), false);
    }

    /**
     * 通过校验后，获取openId
     * @return
     */
    private String getOpenId(String guid){
        WechatOpenAccount wechatOpenAccount = getBeanByContext(WechatOpenAccountService.class).queryOne(WechatOpenAccountQueryBody.builder().guid(guid).type(WechatOpenidType.PUBLIC).isSubscribe(true).build());
        return null == wechatOpenAccount ? null : wechatOpenAccount.getOpenId();
    }

    /**
     * @param guid    用户ID 获取openId
     * @param templateId   模版ID
     * @param templateDesc  模版描述
     * @param keyValue  内容
     * @return
     */
    public Response sendByGuid(String guid, String templateId, String templateDesc, List<Object> pathParams, String... keyValue) {
        String openId = this.getOpenId(guid);
        if (StringUtils.isEmpty(openId)) {
            return Response.failure(String.format("公众号通知失败- %s %s OpenId不能为空",templateDesc, guid));
        } else {
            return this.send(getOpenId(guid),templateId,templateDesc,pathParams,keyValue);
        }
    }

    public Response send(String openId, String templateId, String templateDesc, List<Object> pathParams, String... keyValue) {
        return this.send(null, openId, templateId, templateDesc, pathParams, keyValue);
    }

    public Response send(String appid, String openId, String templateId, String templateDesc, List<Object> pathParams, String... keyValue) {
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
            if (StringUtils.isBlank(appid)) {
                appid = ContextHolder.getContext().getBean(WxMaConfig.class).getAppid();
            }
            BibleItem bibleItem = getBeanByContext(BibleItemService.class).queryOne(BibleItemQO.builder().parentCode(WECHAT_MP_TEMPLATE).code(templateId).build());
            ApiAssert.notNull(bibleItem, WechatMaCode.Message.MA_PATH_NOT_NULL, templateId);

            WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
            wxMpTemplateMessage.setData(data);
            wxMpTemplateMessage.setToUser(openId);
            wxMpTemplateMessage.setTemplateId(bibleItem.getValue());

            wxMpTemplateMessage.setMiniProgram(new MiniProgram(appid, CollectionUtils.isEmpty(pathParams) ? bibleItem.getAttribute1() : String.format(bibleItem.getAttribute1(),pathParams.toArray()),false));

            //发送模板消息
            String templateMsg = wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
            return Response.success(templateMsg);
        } catch (WxErrorException e) {
            log.error(String.format("微信公众号通知失败-%s: %s",templateDesc, e.getError()));
            return Response.failure(e.getError().getErrorCode(), templateDesc + e.getMessage());
        } catch (Exception e) {
            log.error(String.format("微信公众号通知失败-%s: %s",templateDesc, e.getMessage()));
            return Response.failure(templateDesc + e.getMessage());
        }
    }

}

