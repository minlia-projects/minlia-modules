package com.minlia.module.richtext.constant;

import com.minlia.cloud.code.Code;

/**
 * @author garen
 * @date 2018/9/14
 */
public class RichtextCode {

    public enum Message implements Code {

        /**
         * {0}模版不存在
         */
        NOT_EXISTS,

        /**
         * 已存在
         */
        ALREADY_EXISTS;

        @Override
        public String module() {
            return RichtextConstants.MODULE_NAME;
        }
    }
}