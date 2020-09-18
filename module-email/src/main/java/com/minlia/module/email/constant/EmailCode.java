package com.minlia.module.email.constant;

import com.minlia.cloud.code.Code;

/**
 * @author garen
 * @date 2018/9/14
 */
public class EmailCode {

    public enum Message implements Code {

        /**
         * 邮件发送成功
         */
        SEND_SUCCESSFUL,

        /**
         * 邮件发送失败
         */
        SEND_FAILED;

        @Override
        public String module() {
            return EmailConstants.MODULE_NAME;
        }

    }

}