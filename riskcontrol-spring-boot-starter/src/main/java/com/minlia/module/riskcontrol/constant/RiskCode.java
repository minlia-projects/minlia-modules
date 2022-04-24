package com.minlia.module.riskcontrol.constant;

import com.minlia.cloud.code.Code;

/**
 * @author garen
 * @date 2018/9/14
 */
public class RiskCode {

    public enum Message implements Code {

        /**
         * 黑名单，禁止访问
         */
        BLACK_IP,

        /**
         * 不允许的IP范围
         */
        BLACK_IP_SCOPE,

        /**
         * 同一个帐户{0}分钟内使用不同的IP登录系统,count={1}
         */
        NUM_DIFF_IP_LOGIN_MINS,

        /**
         * 近15分钟内同ip出现多次登陆失败,count=
         */
        NUM_SAME_IP_LOGIN_FAILURE_MINS,

        SAME_ACCOUNT_DIFFERENT_LOGIN_IP;

        @Override
        public String module() {
            return RiskSecurityConstants.MODULE_NAME;
        }

    }

}
