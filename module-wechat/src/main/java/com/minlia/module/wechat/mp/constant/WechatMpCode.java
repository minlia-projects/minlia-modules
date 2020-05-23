package com.minlia.module.wechat.mp.constant;

import com.google.common.base.CaseFormat;
import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;
import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.module.common.constant.SymbolConstants;

import java.util.StringJoiner;

/**
 * Created by Calvin On 2017/12/16.
 * API响应码
 */
public class WechatMpCode {

    final static String CODE_PREFIX = MinliaConstants.APP_NAME + ".wxmp";

    public enum Message implements Code {

        /**
         * 该微信号已经绑定其他手机号码
         */
        OPENID_ALREADY_BIND,

        /**
         * 获取微信Session失败
         */
        GET_SESSION_FAILURE,

        UNION_ID_NOT_NULL,

        OPEN_ID_NOT_NULL;

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
