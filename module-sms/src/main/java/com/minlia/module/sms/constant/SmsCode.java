package com.minlia.module.sms.constant;

import com.minlia.cloud.code.Code;

/**
 * @author garen
 * @date 2018/9/14
 */
public class SmsCode {

    public enum Message implements Code {

        /**
         * 短信发送成功
         */
        SEND_SUCCESSFUL,

        /**
         * 短信发送失败
         */
        SEND_FAILED;

        @Override
        public String module() {
            return SmsConstants.MODULE_NAME;
        }

    }

}
