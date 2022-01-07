package com.minlia.module.pay.constant;


import com.minlia.cloud.code.Code;

/**
 * @author garen
 * @date 2018.2.9
 */
public class SysPayCode {

    public enum Message implements Code {

        ORDER_ALREADY_FINISHED;

        @Override
        public String module() {
            return SysPayConstants.MODULE_NAME;
        }

    }

}