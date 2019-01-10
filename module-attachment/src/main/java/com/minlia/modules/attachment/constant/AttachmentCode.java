package com.minlia.modules.attachment.constant;

import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;

public class AttachmentCode {

    public enum Message implements Code {

        /**
         * 附件不存在
         */
        NOT_EXISTS(101500,"system.attachment.message.101500"),

        /**
         * 附件eTag不存在
         */
        ETAG_NOT_EXISTS(101501,"system.attachment.message.101501"),

        /**
         * 附件eTag已绑定
         */
        ETAG_ALREADY_BIND(101502,"system.attachment.message.101502"),

        /**
         * 存储类型未设置或设置有误
         */
        UNSUPPORTED_OSS_TYPE(101503,"system.attachment.message.101503");

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
