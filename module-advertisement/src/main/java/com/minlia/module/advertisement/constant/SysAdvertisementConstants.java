package com.minlia.module.advertisement.constant;


import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 *
 * @author garen
 * @date 2017/10/6
 */
public class SysAdvertisementConstants {

    public final static String MODULE_NAME = MinliaConstants.APP_NAME + ".ad";

    public final static String AUTHORIZE_CREATE = MODULE_NAME + SecurityConstant.OPERATION_CREATE_CODE;
    public final static String AUTHORIZE_UPDATE = MODULE_NAME + SecurityConstant.OPERATION_UPDATE_CODE;
    public final static String AUTHORIZE_DELETE = MODULE_NAME + SecurityConstant.OPERATION_DELETE_CODE;
    public final static String AUTHORIZE_SEARCH = MODULE_NAME + SecurityConstant.OPERATION_SEARCH_CODE;
    public final static String AUTHORIZE_READ = MODULE_NAME + SecurityConstant.OPERATION_READ_CODE;

}
