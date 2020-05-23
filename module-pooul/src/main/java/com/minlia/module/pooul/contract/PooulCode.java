package com.minlia.module.pooul.contract;

import com.google.common.base.CaseFormat;
import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;
import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.module.common.constant.SymbolConstants;

import java.util.StringJoiner;

/**
 * Created by garen on 2018/9/14.
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
        public String code() {
            return this.name();
        }

        @Override
        public String i18nKey() {
            return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, new StringJoiner(SymbolConstants.DOT)
                    .add(CODE_PREFIX)
                    .add(this.getClass().getSimpleName())
                    .add(this.name()).toString());
        }

        @Override
        public String message(Object... args) {
            return Lang.get(this.i18nKey(), args);
        }

    }

}
