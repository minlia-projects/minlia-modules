package com.minlia.modules.aliyun.oss.api.constant;

import com.minlia.cloud.code.Code;

/**
 * @author will
 * @date 4/16/17
 * API错误代码索引
 */
public class AliyunOssCode {

    final static String CODE_PREFIX = "aliyun.oss";

    public enum Message implements Code {

        /**
         * 阿里云OSS上传失败-{0}
         */
        UPLOAD_FAILURE,

        /**
         * 阿里云OSS访问ID未配置
         */
        ACCESSKEYID_NOT_CONFIG,

        /**
         * 阿里云OSS访问密钥未配置
         */
        SECRETACCESSKEY_NOT_CONFIG,

        /**
         * 阿里云OSS存储桶未配置
         */
        BUCKET_NOT_CONFIG,

        /**
         * 阿里云OSS端点未配置
         */
        ENDPOINT_NOT_CONFIG;

        @Override
        public String module() {
            return CODE_PREFIX;
        }

    }

}