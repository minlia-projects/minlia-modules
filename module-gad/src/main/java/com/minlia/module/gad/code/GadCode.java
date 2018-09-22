package com.minlia.module.gad.code;

import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;

/**
 * Created by Calvin On 2017/12/16.
 * API响应码
 */
public class GadCode {

    public GadCode(){
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
         * 高德地图 web api key 未配置
         */
        WEB_API_KEY_NOT_FOUND(101200,"system.gad.message.101200"),

        /**
         * 高德云图 table id 未配置
         */
        WEB_TABLE_ID_NOT_FOUND(101201,"system.gad.message.101201"),

        /**
         * 获取高德地理位置失败
         */
        REGEO_FAILURE(101202,"system.gad.message.101202");

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
