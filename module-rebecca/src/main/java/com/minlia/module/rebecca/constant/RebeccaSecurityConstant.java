package com.minlia.module.rebecca.constant;

import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * @author garen
 */
public class RebeccaSecurityConstant {

    public final static String ORGANIZATION = MinliaConstants.APP_NAME + ".organization";
    public final static String ORGANIZATION_CREATE = ORGANIZATION + SecurityConstant.OPERATION_CREATE_CODE;
    public final static String ORGANIZATION_UPDATE = ORGANIZATION + SecurityConstant.OPERATION_UPDATE_CODE;
    public final static String ORGANIZATION_DELETE = ORGANIZATION + SecurityConstant.OPERATION_DELETE_CODE;
    public final static String ORGANIZATION_SEARCH = ORGANIZATION + SecurityConstant.OPERATION_SEARCH_CODE;

}
