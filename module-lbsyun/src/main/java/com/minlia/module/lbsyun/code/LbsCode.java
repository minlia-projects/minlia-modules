package com.minlia.module.lbsyun.code;

import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;

/**
 * Created by garen On 2017/12/16.
 */
public class LbsCode {

    public LbsCode(){
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
         * 百度LBS创建失败：{0}
         */
        CREATE_FAILURE(101400,"system.lbs.message.101400"),

        /**
         * 百度LBS更新失败：{0}
         */
        UPDATE_FAILURE(101401,"system.lbs.message.101401");

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