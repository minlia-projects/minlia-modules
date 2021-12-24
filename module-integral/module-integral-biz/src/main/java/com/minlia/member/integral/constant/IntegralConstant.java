package com.minlia.member.integral.constant;

import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/10/12 10:49 AM
 */
public class IntegralConstant {

    public static final String MODULE_NAME = MinliaConstants.APP_NAME + ".integral";

    public class Authorize {

        public class Config {
            public static final String NAME = MODULE_NAME + ".config";
            public static final String CREATE = NAME + SecurityConstant.OPERATION_CREATE_CODE;
            public static final String UPDATE = NAME + SecurityConstant.OPERATION_UPDATE_CODE;
            public static final String DELETE = NAME + SecurityConstant.OPERATION_DELETE_CODE;
            public static final String SELECT = NAME + SecurityConstant.OPERATION_SEARCH_CODE;
        }

        public class Record {
            public static final String NAME = MODULE_NAME + ".record";
            public static final String CREATE = NAME + SecurityConstant.OPERATION_CREATE_CODE;
            public static final String UPDATE = NAME + SecurityConstant.OPERATION_UPDATE_CODE;
            public static final String DELETE = NAME + SecurityConstant.OPERATION_DELETE_CODE;
            public static final String SELECT = NAME + SecurityConstant.OPERATION_SEARCH_CODE;
            public static final String READ = NAME + SecurityConstant.OPERATION_READ_CODE;
        }

        public class User {
            public static final String NAME = MODULE_NAME + ".record";
            public static final String SELECT = NAME + SecurityConstant.OPERATION_SEARCH_CODE;
            public static final String READ = NAME + SecurityConstant.OPERATION_READ_CODE;
        }

    }

}