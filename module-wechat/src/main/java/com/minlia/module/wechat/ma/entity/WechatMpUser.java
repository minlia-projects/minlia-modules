package com.minlia.module.wechat.ma.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.wechat.ma.enumeration.Gender;
import lombok.*;


/**
 * 微信开放用户详情
 * 根据encryptedData解密出来的用户详情  主要参数unionid
 * @author: Minlia Speedup Code Engine
 * @since: 1.0.0.RELEASE
 * Bible Define as a JPA entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@JsonPropertyOrder({})
@JsonIgnoreProperties(value = {})
public class WechatMpUser extends AbstractEntity {

    /**
     * 系统用户编号
     */
    private String guid;

    /**
     * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
     */
    private String subscribe;

    /**
     * 联合编号
     */
    private String unionid;

    /**
     * 开放的用户编号(小程序)
     */
    private String openid;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 性别
     */
    private Gender sex;

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
     * 用户的语言，简体中文为zh_CN
     */
    private String language;

    /**
     * 头像网址
     */
    private String headimgurl;

}

