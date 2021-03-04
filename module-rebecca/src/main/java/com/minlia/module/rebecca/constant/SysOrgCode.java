package com.minlia.module.rebecca.constant;

import com.minlia.cloud.code.Code;
import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * @author garen
 * @date 2018/9/14
 */
public class SysOrgCode {

    public final static String MODULE_NAME = MinliaConstants.APP_NAME + ".org";
    public final static String CREATE = MODULE_NAME + SecurityConstant.OPERATION_CREATE_CODE;
    public final static String UPDATE = MODULE_NAME + SecurityConstant.OPERATION_UPDATE_CODE;
    public final static String DELETE = MODULE_NAME + SecurityConstant.OPERATION_DELETE_CODE;
    public final static String SELECT = MODULE_NAME + SecurityConstant.OPERATION_SEARCH_CODE;

    public enum Message implements Code {

        NOT_EXISTS,

        ALREADY_EXISTS,

        PARENT_NOT_EXISTS;

        @Override
        public String module() {
            return MODULE_NAME;
        }

    }

}