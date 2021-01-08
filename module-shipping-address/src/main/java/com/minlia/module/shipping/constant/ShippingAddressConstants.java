package com.minlia.module.shipping.constant;


import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * Created by garen on 2017/10/6.
 */
public class ShippingAddressConstants {

    public final static String MODULE_NAME = MinliaConstants.APP_NAME + ".shipping_address";

    public class Authorize {
        public static final String CREATE = MODULE_NAME + SecurityConstant.OPERATION_CREATE_CODE;
        public static final String UPDATE = MODULE_NAME + SecurityConstant.OPERATION_UPDATE_CODE;
        public static final String DELETE = MODULE_NAME + SecurityConstant.OPERATION_DELETE_CODE;
        public static final String SEARCH = MODULE_NAME + SecurityConstant.OPERATION_SEARCH_CODE;
        public static final String READ = MODULE_NAME + SecurityConstant.OPERATION_READ_CODE;
    }

}