package com.minlia.modules.qcloud.start.constant;


import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;

/**
 * Created by garen on 2018/4/19.
 * API响应码
 */
public class QcloudCode {

    public QcloudCode() {
        throw new AssertionError();
    }

    public enum Exception implements Code {

        TEST(-1, "TEST");

        private int code;
        private String i18nKey;

        Exception(int code, String i18nKey) {
            this.code = code;
            this.i18nKey = i18nKey;
        }

        @Override
        public int code() {
            return code;
        }

        @Override
        public String i18nKey() {
            return i18nKey;
        }

        @Override
        public String message() {
            return Lang.get(this.i18nKey);
        }

    }

    public enum Message implements Code {

        /**
         * 腾讯云获取AccessToken失败-{0}-{1}
         */
        GET_ACCESS_TOKEN_FAILURE(101000, "system.qcloud.message.101000"),

        /**
         * 腾讯云获取Api Sign Ticket失败-{0}-{1}
         */
        GET_API_SIGN_TICKET_FAILURE(101001, "system.qcloud.message.101001"),

        /**
         * 腾讯云获取Api Nonce Ticket失败-{0}-{1}
         */
        GET_API_NONCE_TICKET_FAILURE(101002, "system.qcloud.message.101002"),

        /**
         * 腾讯云面部识别已认证
         */
        FACEID_ALREADY_AUTHENTICATED(101003, "system.qcloud.message.101003");

        private int code;
        private String i18nKey;

        Message(int code, String i18nKey) {
            this.code = code;
            this.i18nKey = i18nKey;
        }

        @Override
        public int code() {
            return code;
        }

        @Override
        public String i18nKey() {
            return i18nKey;
        }

        @Override
        public String message() {
            return Lang.get(this.i18nKey);
        }

    }

}
