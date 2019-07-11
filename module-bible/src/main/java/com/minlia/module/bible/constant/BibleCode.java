package com.minlia.module.bible.constant;


import com.google.common.base.CaseFormat;
import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;
import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.module.common.constant.SymbolConstants;

import java.util.StringJoiner;

/**
 * Created by will on 6/21/17.
 * API响应码
 */
public class BibleCode {

    final static String CODE_PREFIX = MinliaConstants.APP_NAME + ".bible";

    public enum Message implements Code {

        /**
         * 存在字典子项无法删除
         */
        COULD_NOT_DELETE_HAS_CHILDREN,

        /**
         * 字典父级不存在
         */
        PARENT_NOT_EXISTS;

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
        public String message(String... args) {
            return Lang.get(this.i18nKey(), args);
        }

    }

}
