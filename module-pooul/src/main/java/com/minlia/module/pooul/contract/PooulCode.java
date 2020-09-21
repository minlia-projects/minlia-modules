package com.minlia.module.pooul.contract;

import com.minlia.cloud.code.Code;
import com.minlia.module.common.constant.MinliaConstants;

/**
 * @author garen
 * @date 2018/9/14
 */
public class PooulCode {

    final static String CODE_PREFIX = MinliaConstants.APP_NAME + ".pooul";

    public enum Message implements Code {

        /**
         * 登录支付平台失败-{0}-{1}
         */
        LOGIN_FAILURE,

        /**
         * Pooul创建订单失败-{0}
         */
        ORDER_CREATE_FAILURE,

        /**
         * Pooul查询订单失败{0}
         */
        ORDER_QUERY_FAILURE,

        /**
         * Pooul关闭订单失败{0}
         */
        ORDER_CLOSE_FAILURE;

        @Override
        public String module() {
            return CODE_PREFIX;
        }

    }

}
