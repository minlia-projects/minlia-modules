package com.minlia.aliyun.green.constant;

import com.minlia.cloud.code.Code;
import com.minlia.module.common.constant.MinliaConstants;

/**
 * @author garen
 * @date 2018/9/14
 */
public class AliyunGreenCode {

    public enum Message implements Code {

        spam,
        politics,
        abuse,
        terrorism,
        porn,
        flood,
        contraband,
        ad;

        @Override
        public String module() {
            return MinliaConstants.APP_NAME + ".aliyun.antispam";
        }

    }

}
