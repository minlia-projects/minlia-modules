package com.minlia.module.richtext.constant;


import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * @author garen
 * @date 8/27/17
 */
public class RichtextConstants {

    public RichtextConstants() {
        throw new AssertionError();
    }

    public static final String MODULE_NAME = MinliaConstants.APP_NAME + ".richtext";
    public static final String CREATE = MODULE_NAME + SecurityConstant.OPERATION_CREATE_CODE;
    public static final String UPDATE = MODULE_NAME + SecurityConstant.OPERATION_UPDATE_CODE;
    public static final String DELETE = MODULE_NAME + SecurityConstant.OPERATION_DELETE_CODE;
    public static final String SEARCH = MODULE_NAME + SecurityConstant.OPERATION_SEARCH_CODE;

}
