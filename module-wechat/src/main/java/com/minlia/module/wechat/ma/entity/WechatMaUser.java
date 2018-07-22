package com.minlia.module.wechat.ma.entity;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.data.entity.WithIdEntity;
import com.minlia.module.wechat.ma.enumeration.Gender;
import lombok.Data;


/**
 * 微信开放用户详情
 * 根据encryptedData解密出来的用户详情  主要参数unionid
 * @author: Minlia Speedup Code Engine
 * @since: 1.0.0.RELEASE
 * Bible Define as a JPA entity
 */
@Data
public class WechatMaUser extends AbstractEntity {

    /**
     * 系统用户编号
     */
    private String guid;

    /**
     * 联合编号
     */
    private String unionId;

    /**
     * 开放的用户编号(小程序)
     */
    private String openId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 性别
     */
    private Gender gender;

    /**
     * 城市
     */
    private String city;

    /**
     * 省份
     */
    private String province;

    /**
     * 国家
     */
    private String country;

    /**
     * 头像网址
     */
    private String avatarUrl;

    /**
     * 水印
     */
    private WxMaUserInfo.Watermark watermark;

    @Data
    public class Watermark {
        private String timestamp;
        private String appid;
    }

}

