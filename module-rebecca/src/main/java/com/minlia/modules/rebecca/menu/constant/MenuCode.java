package com.minlia.modules.rebecca.menu.constant;

import com.google.common.base.CaseFormat;
import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;
import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.module.common.constant.SymbolConstants;

import java.util.StringJoiner;

/**
 * Created by garen on 2018/9/14.
 */
public class MenuCode {

    final static String CODE_PREFIX = MinliaConstants.APP_NAME + ".menu";

    public enum Message implements Code{

        /**
         * 导航不存在
         */
        NOT_EXISTS,

        /**
         * 导航已存在
         */
        ALREADY_EXISTS,

        /**
         * 导航父级不存在
         */
        PARENT_NOT_EXISTS,

        /**
         * 有子导航不能删除
         */
        CAN_NOT_DELETE_HAS_CHILDREN;

        @Override
        public String code() {
            return this.name();
        }

        @Override
        public String i18nKey() {
            return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, new StringJoiner(SymbolConstants.DOT).add(CODE_PREFIX).add(this.getClass().getSimpleName()).add(this.name()).toString());
        }

        @Override
        public String message(String... args) {
            return Lang.get(this.i18nKey(), args);
        }

    }

}
