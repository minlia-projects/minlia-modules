package com.minlia.module.riskcontrol.constant;


import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * @author garen
 */
public class RiskConstants {

    public final static String MODULE_NAME = MinliaConstants.APP_NAME + ".riskcontrol";

    public static class Cache {
        /**
         * 数据库
         */
        public final static int DB_INDEX = 2;
        /**
         * 规则
         */
        public final static String DROOL = "risk:drool";
        /**
         * 黑名单IP
         */
        public final static String BLACK_IP = "risk:black:ip";
        /**
         * 黑名单IP范围
         */
        public final static String BLACK_IP_SCOPE = "risk:black:ip:scope";
        /**
         * 白名单URL
         */
        public final static String WHITE_URL = "risk:white:url";
    }

    public static class Authority {
        public final static String CREATE = MODULE_NAME + SecurityConstant.OPERATION_SAVE_CODE;
        public final static String UPDATE = MODULE_NAME + SecurityConstant.OPERATION_UPDATE_CODE;
        public final static String DELETE = MODULE_NAME + SecurityConstant.OPERATION_DELETE_CODE;
        public final static String SELECT = MODULE_NAME + SecurityConstant.OPERATION_SEARCH_CODE;
    }

}