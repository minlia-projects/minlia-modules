package com.minlia.module.todo.constant;


import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 *
 * @author garen
 * @date 2018/4/27
 */
public class TodoConstants {

    public TodoConstants() {
        throw new AssertionError();
    }

    public static final String MODULE_NAME = MinliaConstants.APP_NAME + ".todo";
    public static final String CREATE = MODULE_NAME + SecurityConstant.OPERATION_CREATE_CODE;
    public static final String UPDATE = MODULE_NAME + SecurityConstant.OPERATION_UPDATE_CODE;
    public static final String DELETE = MODULE_NAME + SecurityConstant.OPERATION_DELETE_CODE;
    public static final String SEARCH = MODULE_NAME + SecurityConstant.OPERATION_SEARCH_CODE;

}
