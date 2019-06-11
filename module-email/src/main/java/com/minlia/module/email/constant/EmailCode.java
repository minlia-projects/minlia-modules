package com.minlia.module.email.constant;

import com.google.common.base.CaseFormat;
import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;
import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.module.common.constant.SymbolConstants;

import java.util.StringJoiner;

/**
 * Created by garen on 2018/9/14.
 */
public class EmailCode {

    final static String CODE_PREFIX = MinliaConstants.APP_NAME + ".email";

    public enum Message implements Code {

        /**
         * 邮件发送成功
         */
        SEND_SUCCESSFUL,

        /**
         * 邮件发送失败
         */
        SEND_FAILED;

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
        public String message() {
            return Lang.get(this.i18nKey());
        }

    }

}