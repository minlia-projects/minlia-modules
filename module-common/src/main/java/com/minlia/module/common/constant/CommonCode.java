package com.minlia.module.common.constant;

import com.google.common.base.CaseFormat;
import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;

import java.util.StringJoiner;

/**
 * Created by garen on 2018/9/14.
 */
public class CommonCode {

    final static String CODE_PREFIX = MinliaConstants.APP_NAME + ".common";

    public enum Message implements Code {

        /**
         * 成功
         */
        SUCCESS,

        /**
         * 失败
         */
        FAILURE,

        /**
         * 请输入有效的用户名
         */
        USERNAME_FORMAT_ERROR,

        /**
         * 请输入11位有效手机号码
         */
        CELLPHONE_FORMAT_ERROR,

        /**
         * 请输有效邮箱地址
         */
        EMAIL_FORMAT_ERROR,

        /**
         * 请输入有效银行卡号
         */
        BANK_CARD_FORMAT_ERROR,

        /**
         * 请输入有效身份证号码
         */
        ID_CARD_FORMAT_ERROR,

        /**
         * 请输入2-6位中文姓名
         */
        NAME_ZH_FORMAT_ERROR,

        /**
         * 密码长度在6-16,需包含小写字母、大写字母、数字、特殊符号的两种及以上
         */
        PASSWORD_FORMAT_ERROR;

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
        public String message(Object... var1){
            return Lang.get(this.i18nKey(), var1);
        }

    }

}
