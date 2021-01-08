package com.minlia.module.currency.constant;

import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * @author garen
 * <p>
 * 常量
 */
public class SysCurrencyConstant {

    public final static String MODULE_NAME = MinliaConstants.APP_NAME + ".currency";


    public class Authorize {
        public final static String CREATE = MODULE_NAME + SecurityConstant.OPERATION_CREATE_CODE;
        public final static String UPDATE = MODULE_NAME + SecurityConstant.OPERATION_UPDATE_CODE;
        public final static String DELETE = MODULE_NAME + SecurityConstant.OPERATION_DELETE_CODE;
        public final static String SELECT = MODULE_NAME + SecurityConstant.OPERATION_SEARCH_CODE;
    }

}