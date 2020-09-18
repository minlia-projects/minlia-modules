package com.minlia.module.address.constant;


import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * Created by garen on 2017/10/6.
 */
public class AddressConstants {

    public final static String MODULE_NAME = MinliaConstants.APP_NAME + ".address";

    public final static String CREATE = MODULE_NAME + SecurityConstant.OPERATION_CREATE_CODE;

    public final static String UPDATE = MODULE_NAME + SecurityConstant.OPERATION_UPDATE_CODE;

    public final static String DELETE = MODULE_NAME + SecurityConstant.OPERATION_DELETE_CODE;

    public final static String SEARCH = MODULE_NAME + SecurityConstant.OPERATION_SEARCH_CODE;

    public final static String READ = MODULE_NAME + SecurityConstant.OPERATION_READ_CODE;

}
