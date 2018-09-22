package com.minlia.module.wechat.ma.constant;


import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;

/**
 * Created by will on 6/21/17.
 * API响应码
 */
public class WechatMaCode {

    public WechatMaCode() {
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
         * 小程序路径%s不能为空
         */
        MA_PATH_NOT_NULL(100900,"system.ma.message.100900"),

        /**
         * 小程序参数{type}未配置
         */
        PARAMETER_NOT_CONFIG(100901,"system.ma.message.100901");

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
