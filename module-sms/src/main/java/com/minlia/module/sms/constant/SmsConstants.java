package com.minlia.module.sms.constant;


import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * @author garen
 * @date 2017/10/6
 */
public class SmsConstants {

    public final static String MODULE_NAME = MinliaConstants.APP_NAME + ".sms";

    public final static String CREATE = MODULE_NAME + SecurityConstant.OPERATION_CREATE_CODE;

    public final static String DELETE = MODULE_NAME + SecurityConstant.OPERATION_DELETE_CODE;

    public final static String SEARCH = MODULE_NAME + SecurityConstant.OPERATION_SEARCH_CODE;

    public final static String READ = MODULE_NAME + SecurityConstant.OPERATION_READ_CODE;

}
