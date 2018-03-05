package com.minlia.module.wechat.ma.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.wechat.ma.enumeration.WechatOpenidType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 微信公众号
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
public class WechatOpenAccount extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @JsonProperty
    private String guid;

    /**
     * 联合编号
     */
    @JsonProperty
    private String unionId;

    /**
     * 开放的用户编号
     */
    @JsonProperty
    private String openId;

    /**
     * 临时编码
     */
    @JsonProperty
    private String wxCode;

    /**
     * openId类型
     */
    @JsonProperty
    private WechatOpenidType type;

    /**
     * openId子项
     */
    @JsonProperty
    private String subitem;

    /**
     * 是否关注
     */
    @JsonIgnore
    private Boolean isSubscribe;

    /**
     * 手机号码
     */
    @JsonProperty
    private String cellphone;

    /**
     * 明文密码
     */
    @ApiModelProperty(value = "明文密码",example = "111111")
    @JsonProperty
    private String rawPassword;

}
