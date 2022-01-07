package com.minlia.module.pay.constant;


import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * @author garen
 */
public class SysPayConstants {

    public final static String MODULE_NAME = MinliaConstants.APP_NAME + ".pay";

    public static class Authorize {
        public final static String READ = MODULE_NAME + SecurityConstant.OPERATION_READ_CODE;
        public final static String SELECT = MODULE_NAME + SecurityConstant.OPERATION_SEARCH_CODE;
        public final static String REVIEW = MODULE_NAME + SecurityConstant.OPERATION_REVIEW_CODE;
        public final static String AUTH = MODULE_NAME + SecurityConstant.OPERATION_AUTHENTICATION_CODE;
    }

}