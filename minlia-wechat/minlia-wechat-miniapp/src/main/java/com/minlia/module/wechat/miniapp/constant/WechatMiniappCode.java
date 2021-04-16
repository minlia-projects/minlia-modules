package com.minlia.module.wechat.miniapp.constant;

import com.minlia.cloud.code.Code;

/**
 * @author garen
 * @date 2018/9/14
 */
public class WechatMiniappCode {

    public enum Message implements Code {

        /**
         * 未注册
         */
        UNREGISTERED;

        @Override
        public String module() {
            return WechatMiniappConstant.MODULE_NAME;
        }
    }
}