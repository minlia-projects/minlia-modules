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

    public static final String ENTITY = MinliaConstants.APP_NAME + ".richtext";
    public static final String CREATE = ENTITY + SecurityConstant.OPERATION_CREATE_CODE;
    public static final String UPDATE = ENTITY + SecurityConstant.OPERATION_UPDATE_CODE;
    public static final String DELETE = ENTITY + SecurityConstant.OPERATION_DELETE_CODE;
    public static final String SEARCH = ENTITY + SecurityConstant.OPERATION_SEARCH_CODE;

}
