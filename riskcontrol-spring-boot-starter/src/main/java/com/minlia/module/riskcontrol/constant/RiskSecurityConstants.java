package com.minlia.module.riskcontrol.constant;


import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

public class RiskSecurityConstants {

    private final static String MODULE = MinliaConstants.APP_NAME + ".risk";

    private final static String BLANK_LIST = MODULE + "_blank_list";
    public final static String BLANK_LIST_SAVE = BLANK_LIST + SecurityConstant.OPERATION_SAVE_CODE;
    public final static String BLANK_LIST_RESET = BLANK_LIST + SecurityConstant.OPERATION_RESET_CODE;
    public final static String BLANK_LIST_DELETE = BLANK_LIST + SecurityConstant.OPERATION_DELETE_CODE;
    public final static String BLANK_LIST_SEARCH = BLANK_LIST + SecurityConstant.OPERATION_SEARCH_CODE;


    private final static String OFFER = "bmp.offer";
    public final static String OFFER_CREATE = OFFER + SecurityConstant.OPERATION_CREATE_CODE;
    public final static String OFFER_SEARCH = OFFER + SecurityConstant.OPERATION_SEARCH_CODE;
    public final static String OFFER_ACCEPT = OFFER + ".accept";

}
