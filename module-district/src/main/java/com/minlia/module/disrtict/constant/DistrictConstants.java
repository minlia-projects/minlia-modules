package com.minlia.module.disrtict.constant;

import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * @author garen
 * @date 2018/9/13
 */
public class DistrictConstants {

    public static final String MODULE_NAME = MinliaConstants.APP_NAME + ".district";

    public static final String CREATE = MODULE_NAME + SecurityConstant.OPERATION_CREATE_CODE;

    public static final String UPDATE = MODULE_NAME + SecurityConstant.OPERATION_UPDATE_CODE;

    public static final String DELETE = MODULE_NAME + SecurityConstant.OPERATION_DELETE_CODE;

    public static final String SEARCH = MODULE_NAME + SecurityConstant.OPERATION_SEARCH_CODE;

    public static final String READ = MODULE_NAME + SecurityConstant.OPERATION_READ_CODE;

}
