package com.minlia.module.country.contract;

import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * @author garen
 * @date 2018/10/15
 */
public class CountryContracts {

    public static final String MODULE = MinliaConstants.APP_NAME + ".country";

    public static final String CREATE = MODULE + SecurityConstant.OPERATION_CREATE_CODE;
    public static final String UPDATE = MODULE + SecurityConstant.OPERATION_UPDATE_CODE;
    public static final String DELETE = MODULE + SecurityConstant.OPERATION_DELETE_CODE;
    public static final String SEARCH = MODULE + SecurityConstant.OPERATION_SEARCH_CODE;

}
