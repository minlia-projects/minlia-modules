package com.minlia.module.address.constant;


import com.google.common.base.CaseFormat;
import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;
import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.module.common.constant.SymbolConstants;

import java.util.StringJoiner;

/**
 * @author garen
 * @date 2018.2.9
 */
public class AddressCode {

    final static String CODE_PREFIX = MinliaConstants.APP_NAME + ".address";

    public enum Message implements Code {

        /**
         * 最多添加5个地址
         */
        ADD_UP_TO_5_ADDRESSES;

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
