package com.minlia.module.attachment.constant;

import com.minlia.cloud.code.Code;

/**
 * @author garen
 */
public class SysAttachmentCode {

    public enum Message implements Code {

        /**
         * 附件不存在
         */
        NOT_EXISTS,

        /**
         * 附件eTag不存在
         */
        ETAG_NOT_EXISTS,

        /**
         * 附件eTag已绑定
         */
        ETAG_ALREADY_BIND,

        /**
         * 存储类型未设置或设置有误
         */
        UNSUPPORTED_OSS_TYPE;

        @Override
        public String module() {
            return SysAttachmentConstant.MODULE_NAME;
        }

    }

}
