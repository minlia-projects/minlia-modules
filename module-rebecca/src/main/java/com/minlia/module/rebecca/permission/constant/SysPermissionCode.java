package com.minlia.module.rebecca.permission.constant;

import com.minlia.cloud.code.Code;
import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * @author garen
 * @date 2018/9/14
 */
public class SysPermissionCode {

    public final static String MODULE_NAME = MinliaConstants.APP_NAME + ".permission";
    public final static String UPDATE = MODULE_NAME + SecurityConstant.OPERATION_UPDATE_CODE;
    public final static String SEARCH = MODULE_NAME + SecurityConstant.OPERATION_SEARCH_CODE;
    public final static String CLEAR = MODULE_NAME + SecurityConstant.OPERATION_CLEAR_CODE;


    public enum Message implements Code {

        /**
         * 权限点已存在
         */
        ALREADY_EXISTED;

        @Override
        public String module() {
            return MODULE_NAME;
        }

    }

}
