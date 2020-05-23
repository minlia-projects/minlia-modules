package com.minlia.module.bible.constant;


import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * Created by will on 8/27/17.
 */
public class BibleConstants {

    public BibleConstants() {
        throw new AssertionError();
    }

    public static final String MODULE = MinliaConstants.APP_NAME + ".bible";

    public static final String CREATE = MODULE + SecurityConstant.OPERATION_CREATE_CODE;
    public static final String UPDATE = MODULE + SecurityConstant.OPERATION_UPDATE_CODE;
    public static final String DELETE = MODULE + SecurityConstant.OPERATION_DELETE_CODE;
    public static final String SEARCH = MODULE + SecurityConstant.OPERATION_SEARCH_CODE;
    public static final String RESET = MODULE + SecurityConstant.OPERATION_RESET_CODE;

}
