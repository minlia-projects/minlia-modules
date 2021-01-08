package com.minlia.module.currency.constant;

import com.minlia.cloud.code.Code;

/**
 * @author garen
 */
public class SysCurrencyCode {

    public enum Message implements Code {

        /**
         * 附件不存在
         */
        NOT_EXISTS,

        /**
         * 附件eTag不存在
         */
        already_EXISTS;

        @Override
        public String module() {
            return SysCurrencyConstant.MODULE_NAME;
        }

    }

}