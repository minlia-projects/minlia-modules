package com.minlia.module.member.constant;


import com.minlia.cloud.code.Code;

/**
 * @author garen
 * @date 2018.2.9
 */
public class SysMemberCode {

    public enum Message implements Code {

        /**
         * 商户未开通会员,请购买会员
         */
        NOT_MEMBER,

        /**
         * 未开通实名认证
         */
        REAL_NAME_NOT_OPENED,

        VERIFY_PASSWORD_FAILURE,

        /**
         * 多次失败，请稍后
         */
        MULTIPLE_FAILURES_PLEASE_WAIT,

        REGISTRATION_SUCCESS,

        REGISTRATION_FAILURE;

        @Override
        public String module() {
            return SysMemberConstants.MODULE_NAME;
        }

    }

}