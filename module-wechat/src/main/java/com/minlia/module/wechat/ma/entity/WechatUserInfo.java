package com.minlia.module.wechat.ma.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class WechatUserInfo extends AbstractEntity {

    /**
     * 系统用户编号
     */
    @JsonProperty
    private String guid;

    /**
     * 联合编号
     */
    @JsonProperty
    private String unionId;

    /**
     * 开放的用户编号(小程序)
     */
    @JsonProperty
    private String openId;

    /**
     * 用户昵称
     */
    @JsonProperty
    private String nickName;

    /**
     * 性别
     */
    @JsonProperty
    private Gender gender;

    /**
     * 城市
     */
    @JsonProperty
    private String city;

    /**
     * 省份
     */
    @JsonProperty
    private String province;

    /**
     * 国家
     */
    @JsonProperty
    private String country;

    /**
     * 头像网址
     */
    @JsonProperty
    private String avatarUrl;

}

