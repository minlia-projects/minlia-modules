package com.minlia.module.todo.constant;


import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * Created by garen on 2018/4/27.
 */
public class TodoConstants {

    public TodoConstants() {
        throw new AssertionError();
    }

    public static final String MODULE = MinliaConstants.APP_NAME + ".todo";
    public static final String CREATE = MODULE + SecurityConstant.OPERATION_CREATE_CODE;
    public static final String UPDATE = MODULE + SecurityConstant.OPERATION_UPDATE_CODE;
    public static final String DELETE = MODULE + SecurityConstant.OPERATION_DELETE_CODE;
    public static final String SEARCH = MODULE + SecurityConstant.OPERATION_SEARCH_CODE;

}
