package com.minlia.module.email.constant;


import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * @author garen
 * @date 2017/10/6
 */
public class EmailConstants {

    public final static String MODULE_NAME = MinliaConstants.APP_NAME + ".email";

    public class Authorize {

        public final static String CREATE = MODULE_NAME + SecurityConstant.OPERATION_CREATE_CODE;
        public final static String UPDATE = MODULE_NAME + SecurityConstant.OPERATION_UPDATE_CODE;
        public final static String DELETE = MODULE_NAME + SecurityConstant.OPERATION_DELETE_CODE;
        public final static String SEARCH = MODULE_NAME + SecurityConstant.OPERATION_SEARCH_CODE;
        public final static String READ = MODULE_NAME + SecurityConstant.OPERATION_READ_CODE;

    }

}