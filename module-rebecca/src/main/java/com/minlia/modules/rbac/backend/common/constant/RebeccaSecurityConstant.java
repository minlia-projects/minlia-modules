package com.minlia.modules.rbac.backend.common.constant;

import com.minlia.modules.security.constant.SecurityConstant;

public class RebeccaSecurityConstant {

    public final static String APPLICATION = "minlia.";

    public final static String USER = APPLICATION + "user";
    public final static String USER_CREATE = USER + SecurityConstant.OPERATION_CREATE_CODE;
    public final static String USER_UPDATE = USER + SecurityConstant.OPERATION_UPDATE_CODE;
    public final static String USER_DELETE = USER + SecurityConstant.OPERATION_DELETE_CODE;
    public final static String USER_SEARCH = USER + SecurityConstant.OPERATION_SEARCH_CODE;
    public final static String USER_GRANT = USER + SecurityConstant.OPERATION_GRANT_CODE;

    public final static String ROLE = APPLICATION + "role";
    public final static String ROLE_CREATE = ROLE + SecurityConstant.OPERATION_CREATE_CODE;
    public final static String ROLE_UPDATE = ROLE + SecurityConstant.OPERATION_UPDATE_CODE;
    public final static String ROLE_DELETE = ROLE + SecurityConstant.OPERATION_DELETE_CODE;
    public final static String ROLE_SEARCH = ROLE + SecurityConstant.OPERATION_SEARCH_CODE;
    public final static String ROLE_GRANT = ROLE + SecurityConstant.OPERATION_GRANT_CODE;

    public final static String PERMISSION = APPLICATION + "permission";
    public final static String PERMISSION_UPDATE = PERMISSION + SecurityConstant.OPERATION_UPDATE_CODE;
    public final static String PERMISSION_SEARCH = PERMISSION + SecurityConstant.OPERATION_SEARCH_CODE;
    public final static String PERMISSION_GRANT = PERMISSION + SecurityConstant.OPERATION_GRANT_CODE;

    public final static String NAVIGATION = APPLICATION + "navigation";
    public final static String NAVIGATION_CREATE = NAVIGATION + SecurityConstant.OPERATION_CREATE_CODE;
    public final static String NAVIGATION_UPDATE = NAVIGATION + SecurityConstant.OPERATION_UPDATE_CODE;
    public final static String NAVIGATION_DELETE = NAVIGATION + SecurityConstant.OPERATION_DELETE_CODE;
    public final static String NAVIGATION_SEARCH = NAVIGATION + SecurityConstant.OPERATION_SEARCH_CODE;
    public final static String NAVIGATION_GRANT = NAVIGATION + SecurityConstant.OPERATION_GRANT_CODE;

}
