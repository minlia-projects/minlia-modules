package com.minlia.hsjs.integral.constant;

import com.minlia.cloud.code.Code;

/**
 * @author garen
 * @date 2018/9/14
 */
public class HsjsIntegralCode {

    public enum Message implements Code {

        /**
         * 不存在
         */
        NOT_EXISTS,

        /**
         * 已存在
         */
        ALREADY_EXISTS,

        /**
         * 父级不存在
         */
        PARENT_NOT_EXISTS,

        /**
         * 有子不能删除
         */
        CAN_NOT_DELETE_HAS_CHILDREN;

        @Override
        public String module() {
            return HsjsIntegralConstant.MODULE_NAME;
        }
    }
}