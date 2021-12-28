package com.minlia.module.realname.constant;


import com.minlia.module.common.constant.MinliaConstants;
import com.minlia.modules.security.constant.SecurityConstant;

/**
 * @author garen
 * @date 2017/10/6
 */
public class SysRealNameConstants {

    public final static String MODULE = MinliaConstants.APP_NAME + ".realname";

    public static class Authorize {
        public final static String CREATE = MODULE + SecurityConstant.OPERATION_CREATE_CODE;
        public final static String UPDATE = MODULE + SecurityConstant.OPERATION_UPDATE_CODE;
        public final static String DELETE = MODULE + SecurityConstant.OPERATION_DELETE_CODE;
        public final static String SEARCH = MODULE + SecurityConstant.OPERATION_SEARCH_CODE;
        public final static String READ = MODULE + SecurityConstant.OPERATION_READ_CODE;
    }

}
