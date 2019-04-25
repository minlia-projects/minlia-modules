package com.minlia.module.wechat.ma.bean.entity;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import lombok.Data;


/**
 * 小程序用户信息
 * @author garen
 */
@Data
public class WechatMaUser extends WxMaUserInfo {

    private Long id;

    /**
     * 小程序应用名称
     */
    private String appName;

    /**
     * 系统用户编号
     */
    private String guid;

    /**
     * 登陆code
     */
    private String code;

}

