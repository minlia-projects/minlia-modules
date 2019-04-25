package com.minlia.module.wallet.constant;


import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * Created by garen on 2017/10/6.
 */
public class WalletSecurityConstants {

    public final static String MODULE_WALLET_CODE = MinliaConstants.APP_NAME + ".wallet";
    public final static String MODULE_WALLET_DESC = "钱包-";

    public final static String WALLET_CREATE_CODE = MODULE_WALLET_CODE + SecurityConstant.OPERATION_CREATE_CODE;
    public final static String WALLET_CREATE_DESC = MODULE_WALLET_DESC + SecurityConstant.OPERATION_CREATE_DESC_CN;

    public final static String WALLET_UPDATE_CODE = MODULE_WALLET_CODE + SecurityConstant.OPERATION_UPDATE_CODE;
    public final static String WALLET_UPDATE_DESC = MODULE_WALLET_DESC + SecurityConstant.OPERATION_UPDATE_DESC_CN;

    public final static String WALLET_DELETE_CODE = MODULE_WALLET_CODE + SecurityConstant.OPERATION_DELETE_CODE;
    public final static String WALLET_DELETE_DESC = MODULE_WALLET_DESC + SecurityConstant.OPERATION_DELETE_DESC_CN;

    public final static String WALLET_SEARCH_CODE = MODULE_WALLET_CODE + SecurityConstant.OPERATION_SEARCH_CODE;
    public final static String WALLET_SEARCH_DESC = MODULE_WALLET_DESC + SecurityConstant.OPERATION_SEARCH_DESC_CN;

    public final static String WALLET_READ_CODE = MODULE_WALLET_CODE + SecurityConstant.OPERATION_READ_CODE;
    public final static String WALLET_READ_DESC = MODULE_WALLET_DESC + SecurityConstant.OPERATION_READ_DESC_CN;

    public final static String MODULE_WITHDRAW_CODE = MinliaConstants.APP_NAME + ".withdraw";
    public final static String MODULE_WITHDRAW_DESC = "提现-";

    public final static String WITHDRAW_APPLY_CODE = MODULE_WITHDRAW_CODE + SecurityConstant.OPERATION_APPLY_CODE;
    public final static String WITHDRAW_APPLY_DESC = MODULE_WITHDRAW_DESC + SecurityConstant.OPERATION_APPLY_DESC_CN;

    public final static String WITHDRAW_PASS_CODE = MODULE_WITHDRAW_CODE + SecurityConstant.OPERATION_PSSS_CODE;
    public final static String WITHDRAW_PASS_DESC = MODULE_WITHDRAW_DESC + SecurityConstant.OPERATION_PSSS_DESC_CN;

    public final static String WITHDRAW_UPDATE_CODE = MODULE_WITHDRAW_CODE + SecurityConstant.OPERATION_UPDATE_CODE;
    public final static String WITHDRAW_UPDATE_DESC = MODULE_WITHDRAW_DESC + SecurityConstant.OPERATION_UPDATE_DESC_CN;

    public final static String WITHDRAW_DELETE_CODE = MODULE_WITHDRAW_CODE + SecurityConstant.OPERATION_DELETE_CODE;
    public final static String WITHDRAW_DELETE_DESC = MODULE_WITHDRAW_DESC + SecurityConstant.OPERATION_DELETE_DESC_CN;

    public final static String WITHDRAW_SEARCH_CODE = MODULE_WITHDRAW_CODE + SecurityConstant.OPERATION_SEARCH_CODE;
    public final static String WITHDRAW_SEARCH_DESC = MODULE_WITHDRAW_DESC + SecurityConstant.OPERATION_SEARCH_DESC_CN;

    public final static String WITHDRAW_READ_CODE = MODULE_WITHDRAW_CODE + SecurityConstant.OPERATION_READ_CODE;
    public final static String WITHDRAW_READ_DESC = MODULE_WITHDRAW_DESC + SecurityConstant.OPERATION_READ_DESC_CN;

}
