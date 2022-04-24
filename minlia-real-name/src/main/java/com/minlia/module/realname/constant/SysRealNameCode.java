package com.minlia.module.realname.constant;


import com.minlia.cloud.code.Code;

/**
 * @author garen
 * @date 2017/10/6
 */
public class SysRealNameCode {

    public enum Message implements Code {

        /**
         * 通道额度超限
         */
        CHANNEL_MORE_THAN_LIMIT,

        /**
         * 已认证
         */
        ALREADY_AUTHENTICATED,

        /**
         * 年龄必须大于18岁
         */
        AGE_MUST_BE_OVER18,

        /**
         * 认证成功
         */
        AUTHENTICATION_SUCCESSFUL,

        /**
         * 身份信息不一致
         */
        IDENTITY_INCONSISTENCY,

        /**
         * 身份信息有误
         */
        INCORRECT_IDENTITY_INFORMATION,

        /**
         * 请求频繁
         */
        FREQUENT_REQUESTS;

        @Override
        public String module() {
            return SysRealNameConstants.MODULE;
        }

    }

}