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
        public String module() {
            return TodoConstants.MODULE_NAME;
        }

    }

}
