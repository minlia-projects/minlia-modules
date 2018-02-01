package com.minlia.module.bible.constant;


import com.minlia.modules.security.constant.SecurityConstant;

/**
 * Created by will on 8/27/17.
 */
public class BibleSecurityConstant {

  public BibleSecurityConstant() {
    throw new AssertionError();
  }

  public static final String ENTITY = "minlia.bible";

  public static final String CREATE = ENTITY + SecurityConstant.OPERATION_CREATE_CODE;
  public static final String UPDATE = ENTITY + SecurityConstant.OPERATION_UPDATE_CODE;
  public static final String DELETE = ENTITY + SecurityConstant.OPERATION_DELETE_CODE;
  public static final String SEARCH = ENTITY + SecurityConstant.OPERATION_SEARCH_CODE;

}
