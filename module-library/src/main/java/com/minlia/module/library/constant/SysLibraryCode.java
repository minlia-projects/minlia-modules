package com.minlia.module.library.constant;

import com.minlia.cloud.code.Code;

/**
 * @author garen
 */
public class SysLibraryCode {

    public enum Message implements Code {

        /**
         * 文件不存在
         */
        NOT_EXISTS;

        @Override
        public String module() {
            return SysLibraryConstant.MODULE_NAME;
        }

    }

}
