package com.minlia.module.todo.constant;


import com.minlia.modules.security.constant.SecurityConstant;

/**
 * Created by garen on 2018/4/27.
 */
public class TodoSecurityConstants {

  public TodoSecurityConstants() {
    throw new AssertionError();
  }

  public static final String MODULE = "minlia.todo";

  public static final String CREATE = MODULE + SecurityConstant.OPERATION_CREATE_CODE;
  public static final String UPDATE = MODULE + SecurityConstant.OPERATION_UPDATE_CODE;
  public static final String DELETE = MODULE + SecurityConstant.OPERATION_DELETE_CODE;
  public static final String SEARCH = MODULE + SecurityConstant.OPERATION_SEARCH_CODE;

}
