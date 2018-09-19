package com.minlia.module.wechat.mp.constant;

import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;

/**
 * Created by Calvin On 2017/12/16.
 * API响应码
 */
public class WechatMpCode {

    public WechatMpCode(){
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
         * 该微信号已经绑定其他手机号码
         */
        OPENID_ALREADY_BIND(100500,"system.common.exception.100500"),

        /**
         * 获取微信Session失败
         */
        GET_SESSION_FAILURE(100500,"system.common.exception.100500"),

        /**
         * 高德云图 table id 未配置
         */
        WEB_TABLE_ID_NOT_FOUND(100501,"system.common.exception.100501");

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
