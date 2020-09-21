package com.minlia.module.bible.constant;


import com.minlia.cloud.code.Code;

/**
 * @author garen
 * @date 2017-06-21
 * API响应码
 */
public class BibleCode {

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
<<<<<<< HEAD
        public String module() {
            return BibleConstants.MODULE_NAME;
=======
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
>>>>>>> dev/garen
        }

    }

}