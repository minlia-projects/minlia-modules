package com.minlia.module.riskcontrol.constant;

import com.minlia.cloud.code.Code;

/**
 * @author garen
 * @date 2018/9/14
 */
public class RiskcontrolCode {

    public enum Message implements Code {

        /**
         * 请勿重复提交
         */
        NO_REPEAT_SUBMIT,

        /**
         * 请求频繁
         */
        FREQUENT_REQUESTS;

        @Override
        public String module() {
            return RiskcontrolConstants.MODULE_NAME;
        }

    }

}