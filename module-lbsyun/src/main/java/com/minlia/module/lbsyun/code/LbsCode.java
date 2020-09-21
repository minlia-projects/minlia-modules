package com.minlia.module.lbsyun.code;

import com.minlia.cloud.code.Code;
import com.minlia.module.common.constant.MinliaConstants;

/**
 * Created by garen On 2017/12/16.
 *
 * @author garen
 */
public class LbsCode {

    final static String CODE_PREFIX = MinliaConstants.APP_NAME + ".baidulbs";

    public enum Message implements Code {

        /**
         * 百度LBS创建失败：{0}
         */
        CREATE_FAILURE,

        /**
         * 百度LBS更新失败：{0}
         */
        UPDATE_FAILURE;

        @Override
        public String module() {
            return CODE_PREFIX;
        }
<<<<<<< HEAD
=======

        @Override
        public String message(Object... var1) {
            return Lang.get(this.i18nKey(), var1);
        }

>>>>>>> dev/garen
    }
}