package com.minlia.module.realname.constant;


import com.minlia.cloud.code.Code;

/**
 * @author garen
 * @date 2017/10/6
 */
public class SysRealNameCode {

    public enum Message implements Code {
        DATA_ALREADY_EXISTED, ALREADY_AUTHENTICATED;

        @Override
        public String module() {
            return SysRealNameConstants.MODULE;
        }
    }

}
