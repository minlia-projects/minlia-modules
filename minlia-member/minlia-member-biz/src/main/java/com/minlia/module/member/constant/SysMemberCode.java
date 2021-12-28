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
         * 未开通个人认证
         */
        PERSONAL_CERTIFICATION_NOT_OPENED,

        REGISTRATION_SUCCESS, REGISTRATION_FAILURE;

        @Override
        public String module() {
            return SysMemberConstants.MODULE_NAME;
        }

    }

}