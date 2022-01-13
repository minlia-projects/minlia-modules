package com.minlia.module.realname.constant;


import com.minlia.cloud.code.Code;

/**
 * @author garen
 * @date 2017/10/6
 */
public class SysRealNameCode {

    public enum Message implements Code {

        /**
         * 已认证
         */
        ALREADY_AUTHENTICATED,

        /**
         * 认证成功
         */
        AUTHENTICATION_SUCCESSFUL,

        /**
         * 姓名身份证不一致
         */
        IDENTITY_INCONSISTENCY,

        /**
         * 身份信息有误
         */
        INCORRECT_IDENTITY_INFORMATION;

        @Override
        public String module() {
            return SysRealNameConstants.MODULE;
        }

    }

}