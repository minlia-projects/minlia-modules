package com.minlia.module.sms.constant;


import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * Created by garen on 2017/10/6.
 */
public class SmsConstants {

    public final static String MODULE = MinliaConstants.APP_NAME + ".sms";

    public final static String CREATE = MODULE + SecurityConstant.OPERATION_CREATE_CODE;

    public final static String DELETE = MODULE + SecurityConstant.OPERATION_DELETE_CODE;

    public final static String SEARCH = MODULE + SecurityConstant.OPERATION_SEARCH_CODE;

    public final static String READ = MODULE + SecurityConstant.OPERATION_READ_CODE;

}