package com.minlia.module.wechat.ma.event;

import com.minlia.module.wechat.login.entity.WechatUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信小程用户授权认证事件
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WechatMaAuthorizedEvent {

    public WechatUser source;

}

