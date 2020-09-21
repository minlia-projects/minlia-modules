package com.minlia.module.todo.constant;


import com.minlia.cloud.code.Code;

/**
 * @author garen
 * @date 2018/4/27
 * API响应码
 */
public class TodoCode {

    public enum Message implements Code {

        /**
         * 待办类型{0}不存在
         */
        TYPE_NOT_EXISTS;

        @Override
<<<<<<< HEAD
        public String module() {
            return TodoConstants.MODULE_NAME;
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
