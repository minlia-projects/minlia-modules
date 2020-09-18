package com.minlia.module.riskcontrol.constant;


import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * @author garen
 */
public class RiskSecurityConstants {

    public final static String MODULE_NAME = MinliaConstants.APP_NAME + ".riskcontrol";

    private final static String BLACK_LIST = MODULE_NAME + "_black_list";
    public final static String BLACK_LIST_SAVE = BLACK_LIST + SecurityConstant.OPERATION_SAVE_CODE;
    public final static String BLACK_LIST_RESET = BLACK_LIST + SecurityConstant.OPERATION_RESET_CODE;
    public final static String BLACK_LIST_DELETE = BLACK_LIST + SecurityConstant.OPERATION_DELETE_CODE;
    public final static String BLACK_LIST_SEARCH = BLACK_LIST + SecurityConstant.OPERATION_SEARCH_CODE;

    private final static String URL_LIST = MODULE_NAME + "_url_list";
    public final static String URL_LIST_SAVE = URL_LIST + SecurityConstant.OPERATION_SAVE_CODE;
    public final static String URL_LIST_RESET = URL_LIST + SecurityConstant.OPERATION_RESET_CODE;
    public final static String URL_LIST_DELETE = URL_LIST + SecurityConstant.OPERATION_DELETE_CODE;
    public final static String URL_LIST_SEARCH = URL_LIST + SecurityConstant.OPERATION_SEARCH_CODE;

    private final static String IP_LIST = MODULE_NAME + "_ip_list";
    public final static String IP_LIST_SAVE = IP_LIST + SecurityConstant.OPERATION_SAVE_CODE;
    public final static String IP_LIST_RESET = IP_LIST + SecurityConstant.OPERATION_RESET_CODE;
    public final static String IP_LIST_DELETE = IP_LIST + SecurityConstant.OPERATION_DELETE_CODE;
    public final static String IP_LIST_SEARCH = IP_LIST + SecurityConstant.OPERATION_SEARCH_CODE;

    private final static String DROOLS_CONFIG = MODULE_NAME + "_drools_config";
    public final static String DROOLS_CONFIG_SAVE = DROOLS_CONFIG + SecurityConstant.OPERATION_SAVE_CODE;
    public final static String DROOLS_CONFIG_RESET = DROOLS_CONFIG + SecurityConstant.OPERATION_RESET_CODE;
    public final static String DROOLS_CONFIG_DELETE = DROOLS_CONFIG + SecurityConstant.OPERATION_DELETE_CODE;
    public final static String DROOLS_CONFIG_SEARCH = DROOLS_CONFIG + SecurityConstant.OPERATION_SEARCH_CODE;

    private final static String RECORD = MODULE_NAME + "_record";
    public final static String RECORD_SEARCH = RECORD + SecurityConstant.OPERATION_SEARCH_CODE;

}
