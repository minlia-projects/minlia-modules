package com.minlia.module.bankcard.constant;


import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

public class BankCardConstants {

    public final static String MODULE_BANKCARD_CODE = MinliaConstants.APP_NAME + ".bankcard";
    public final static String MODULE_BANKCARD_DESC = "银行卡-";

    public final static String BANKCARD_CREATE_CODE = MODULE_BANKCARD_CODE + SecurityConstant.OPERATION_CREATE_CODE;
    public final static String BANKCARD_CREATE_DESC = MODULE_BANKCARD_DESC + SecurityConstant.OPERATION_CREATE_DESC_CN;

    public final static String BANKCARD_UPDATE_CODE = MODULE_BANKCARD_CODE + SecurityConstant.OPERATION_UPDATE_CODE;
    public final static String BANKCARD_UPDATE_DESC = MODULE_BANKCARD_DESC + SecurityConstant.OPERATION_UPDATE_DESC_CN;

    public final static String BANKCARD_DELETE_CODE = MODULE_BANKCARD_CODE + SecurityConstant.OPERATION_DELETE_CODE;
    public final static String BANKCARD_DELETE_DESC = MODULE_BANKCARD_DESC + SecurityConstant.OPERATION_DELETE_DESC_CN;

    public final static String BANKCARD_SEARCH_CODE = MODULE_BANKCARD_CODE + SecurityConstant.OPERATION_SEARCH_CODE;
    public final static String BANKCARD_SEARCH_DESC = MODULE_BANKCARD_DESC + SecurityConstant.OPERATION_SEARCH_DESC_CN;

    public final static String BANKCARD_READ_CODE = MODULE_BANKCARD_CODE + SecurityConstant.OPERATION_READ_CODE;
    public final static String BANKCARD_READ_DESC = MODULE_BANKCARD_DESC + SecurityConstant.OPERATION_READ_DESC_CN;

}
