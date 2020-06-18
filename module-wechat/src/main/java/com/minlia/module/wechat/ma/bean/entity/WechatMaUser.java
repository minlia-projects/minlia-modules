package com.minlia.module.wechat.ma.bean.entity;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 * 小程序用户信息
 *
 * @author garen
 */
@Data
@Entity
public class WechatMaUser extends WxMaUserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "defaultIdGenerator")
    @GenericGenerator(name = "defaultIdGenerator", strategy = "com.minlia.module.data.generator.DefaultIdentityGenerator")
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

