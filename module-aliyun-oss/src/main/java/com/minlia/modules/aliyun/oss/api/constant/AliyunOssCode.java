package com.minlia.modules.aliyun.oss.api.constant;

import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;

/**
 * Created by will on 4/16/17.
 * API错误代码索引
 */
public class AliyunOssCode {

    public AliyunOssCode(){
        throw new AssertionError();
    }

    public enum Exception implements Code {

        /**
         * 上传失败
         */
        UPLOAD_FAILURE(100501,"system.common.exception.100501");

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

        TEST(100501,"system.common.exception.100501");

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
