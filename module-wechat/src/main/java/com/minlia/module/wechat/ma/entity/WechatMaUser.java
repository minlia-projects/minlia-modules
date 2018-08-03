package com.minlia.module.wechat.ma.entity;

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
     * 系统用户编号
     */
    private String guid;

//    /**
//     * 联合编号
//     */
//    private String unionId;
//
//    /**
//     * 开放的用户编号(小程序)
//     */
//    private String openId;
//
//    /**
//     * 用户昵称
//     */
//    private String nickName;
//
//    /**
//     * 性别
//     */
//    private Gender gender;
//
//    /**
//     * 城市
//     */
//    private String city;
//
//    /**
//     * 省份
//     */
//    private String province;
//
//    /**
//     * 国家
//     */
//    private String country;
//
//    /**
//     * 头像网址
//     */
//    private String avatarUrl;
//
//    /**
//     * 水印
//     */
//    private WxMaUserInfo.Watermark watermark;
//
//    @Data
//    public class Watermark {
//        private String timestamp;
//        private String appid;
//    }

}

