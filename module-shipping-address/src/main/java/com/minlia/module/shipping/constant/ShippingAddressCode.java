package com.minlia.module.shipping.constant;

import com.minlia.cloud.code.Code;

/**
 * @author garen
 * @date 2018/9/14
 */
public class ShippingAddressCode {

    public enum Message implements Code {

        /**
         * 不存在
         */
        NOT_EXISTS,

        /**
         * 已存在
         */
        ALREADY_EXISTS,

        ADD_UP_TO_10_ADDRESSES;

        @Override
        public String module() {
            return ShippingAddressConstants.MODULE_NAME;
        }
    }

}