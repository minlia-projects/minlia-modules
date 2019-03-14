package com.minlia.module.address.constant;


import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;

/**
 * @author garen
 * @date 2018.2.9
 */
public class AddressCode {

    public AddressCode(){
        throw new AssertionError();
    }

    public enum Message implements Code {

        /**
         * 最多添加5个地址
         */
        ADD_UP_TO_5_ADDRESSES(200004,"mdl.address.message.200004");

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
