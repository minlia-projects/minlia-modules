package com.minlia.module.account.corporate.constant;

import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;


/**
 * @author garen
 */
public class CorporateAccountConstants {

    public static final String MODULE_NAME = MinliaConstants.APP_NAME + ".corporate_account";

    public class Authorize {
        public static final String CREATE = MODULE_NAME + SecurityConstant.OPERATION_CREATE_CODE;
        public static final String UPDATE = MODULE_NAME + SecurityConstant.OPERATION_UPDATE_CODE;
        public static final String DELETE = MODULE_NAME + SecurityConstant.OPERATION_DELETE_CODE;
        public static final String SELECT = MODULE_NAME + SecurityConstant.OPERATION_SEARCH_CODE;
    }

}