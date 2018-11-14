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

    public enum Message implements Code {

        /**
         * 阿里云OSS上传失败-{0}
         */
        UPLOAD_FAILURE(101100, "aliyun.oss.message.101100"),

        /**
         * 阿里云OSS访问ID未配置
         */
        ACCESSKEYID_NOT_CONFIG(101101, "aliyun.oss.message.101101"),

        /**
         * 阿里云OSS访问密钥未配置
         */
        SECRETACCESSKEY_NOT_CONFIG(101102, "aliyun.oss.message.101102"),

        /**
         * 阿里云OSS存储桶未配置
         */
        BUCKET_NOT_CONFIG(101103, "aliyun.oss.message.101103"),

        /**
         * 阿里云OSS端点未配置
         */
        ENDPOINT_NOT_CONFIG(101104, "aliyun.oss.message.101104");

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
