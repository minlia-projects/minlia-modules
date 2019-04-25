package com.minlia.module.wechat.mp.entity;

import lombok.Data;
import me.chanjar.weixin.mp.bean.result.WxMpUser;


/**
 * 微信开放用户详情
 */
@Data
public class WechatMpUser extends WxMpUser {

    private Long id;

    /**
     * 系统用户编号
     */
    private String guid;

    /**
     * 登陆code
     */
    private String code;

}

