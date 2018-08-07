package com.minlia.module.bank.constants;


import com.minlia.modules.security.constant.SecurityConstant;

/**
 * Created by garen on 2017/10/6.
 */
public class BankSecurityConstant {

    public final static String MODULE_CODE = "mdl.bank";
    public final static String MODULE_DESC = "银行卡-";

    public final static String BANKCODE_CREATE_CODE = MODULE_CODE + SecurityConstant.OPERATION_CREATE_CODE;
    public final static String BANKCODE_CREATE_DESC = MODULE_DESC + SecurityConstant.OPERATION_CREATE_DESC_CN;

    public final static String BANKCODE_UPDATE_CODE = MODULE_CODE + SecurityConstant.OPERATION_UPDATE_CODE;
    public final static String BANKCODE_UPDATE_DESC = MODULE_DESC + SecurityConstant.OPERATION_UPDATE_DESC_CN;

    public final static String BANKCODE_DELETE_CODE = MODULE_CODE + SecurityConstant.OPERATION_DELETE_CODE;
    public final static String BANKCODE_DELETE_DESC = MODULE_DESC + SecurityConstant.OPERATION_DELETE_DESC_CN;

    public final static String BANKCODE_SEARCH_CODE = MODULE_CODE + SecurityConstant.OPERATION_SEARCH_CODE;
    public final static String BANKCODE_SEARCH_DESC = MODULE_DESC + SecurityConstant.OPERATION_SEARCH_DESC_CN;

    public final static String BANKCODE_READ_CODE = MODULE_CODE + SecurityConstant.OPERATION_READ_CODE;
    public final static String BANKCODE_READ_DESC = MODULE_DESC + SecurityConstant.OPERATION_READ_DESC_CN;

}
