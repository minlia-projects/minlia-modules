package com.minlia.module.address.constant;


import com.minlia.cloud.code.Code;

/**
 * @author garen
 * @date 2018.2.9
 */
public class AddressCode {

    public enum Message implements Code {

        /**
         * 最多添加5个地址
         */
        ADD_UP_TO_5_ADDRESSES;

        @Override
        public String module() {
            return AddressConstants.MODULE_NAME;
        }

    }

}
