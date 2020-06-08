package com.minlia.module.wechat.login.entity;


import com.minlia.module.data.entity.AbstractAuditableEntity;
import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.wechat.ma.enumeration.WechatOpenidType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * 微信公众号
 * @author: Minlia Speedup Code Engine
 * @since: 1.0.0.RELEASE
 * Bible Define as a JPA entity
 */
@Entity
@Table(name = "wx_account")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class WechatUser extends AbstractAuditableEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private String guid;

    /**
     * 联合编号
     */
    private String unionId;

    /**
     * 开放的用户编号
     */
    private String openId;

    /**
     * 临时编码
     */
    private String wxCode;

    /**
     * openId类型
     */
    @Enumerated(EnumType.STRING)
    private WechatOpenidType type;

    /**
     * openId子项
     */
    private String subitem;

    /**
     * 是否关注
     */
    private Boolean isSubscribe;

    /**
     * 手机号码
     */
    private String cellphone;

    /**
     * 明文密码
     */
    private String rawPassword;

}
