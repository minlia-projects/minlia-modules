package com.minlia.modules.qcloud.start.constant;


import com.minlia.cloud.code.Code;
import com.minlia.module.common.constant.MinliaConstants;

/**
 * @author garen
 * @date 2018/4/19
 * API响应码
 */
public class QcloudCode {

    final static String CODE_PREFIX = MinliaConstants.APP_NAME + ".qcloud";

    public enum Message implements Code {

        /**
         * 腾讯云获取AccessToken失败-{0}-{1}
         */
        GET_ACCESS_TOKEN_FAILURE,

        /**
         * 腾讯云获取Api Sign Ticket失败-{0}-{1}
         */
        GET_API_SIGN_TICKET_FAILURE,

        /**
         * 腾讯云获取Api Nonce Ticket失败-{0}-{1}
         */
        GET_API_NONCE_TICKET_FAILURE,

        /**
         * 腾讯云面部识别已认证
         */
        FACEID_ALREADY_AUTHENTICATED;

        @Override
        public String module() {
            return CODE_PREFIX;
        }

    }

}
