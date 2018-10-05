package com.minlia.module.pooul.contract;

import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;

/**
 * Created by garen on 2018/9/14.
 */
public class PooulCode {

    public enum Exception implements Code {

        TEST(-1,"TEST");

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
        public String message(){
            return Lang.get(this.i18nKey);
        }

    }

    public enum Message implements Code{

        /**
         * 登录支付平台失败-{0}-{1}
         */
        LOGIN_FAILURE(101300,"system.pooul.message.101300"),

        /**
         * Pooul创建订单失败-{0}
         */
        ORDER_CREATE_FAILURE(101301,"system.pooul.message.101301"),

        /**
         * Pooul查询订单失败{0}
         */
        ORDER_QUERY_FAILURE(101302,"system.pooul.message.101302"),

        /**
         * Pooul关闭订单失败{0}
         */
        ORDER_CLOSE_FAILURE(101303,"system.pooul.message.101303"),

        /**
         * 商户不存在
         */
        MERCHANT_NOT_EXISTS(101304,"system.pooul.message.101304");

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
        public String message(){
            return Lang.get(this.i18nKey);
        }

    }

}
