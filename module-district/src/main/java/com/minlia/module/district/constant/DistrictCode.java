package com.minlia.module.district.constant;

import com.minlia.cloud.code.Code;

/**
 * Created by garen On 2017/12/16.
 *
 * @author garen
 */
public class DistrictCode {

    public enum Message implements Code {

        /**
         * 区域不存在
         */
        NOT_EXISTS,

        /**
         * 区域已存在
         */
        ALREADY_EXISTS;

        @Override
        public String module() {
            return DistrictConstants.MODULE_NAME;
        }
    }

}