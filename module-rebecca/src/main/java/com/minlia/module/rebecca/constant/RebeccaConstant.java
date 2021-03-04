package com.minlia.module.rebecca.constant;

import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * @author garen
 */
public class RebeccaConstant {

    public class Authorize {

        public class Organization {
            public final static String MODULE = MinliaConstants.APP_NAME + ".organization";
            public final static String CREATE = MODULE + SecurityConstant.OPERATION_CREATE_CODE;
            public final static String UPDATE = MODULE + SecurityConstant.OPERATION_UPDATE_CODE;
            public final static String DELETE = MODULE + SecurityConstant.OPERATION_DELETE_CODE;
            public final static String SELECT = MODULE + SecurityConstant.OPERATION_SEARCH_CODE;
        }

        public class DataPermission {
            public final static String MODULE = MinliaConstants.APP_NAME + ".datapermission";
            public final static String CREATE = MODULE + SecurityConstant.OPERATION_CREATE_CODE;
            public final static String UPDATE = MODULE + SecurityConstant.OPERATION_UPDATE_CODE;
            public final static String DELETE = MODULE + SecurityConstant.OPERATION_DELETE_CODE;
            public final static String SELECT = MODULE + SecurityConstant.OPERATION_SEARCH_CODE;
        }

    }

}
