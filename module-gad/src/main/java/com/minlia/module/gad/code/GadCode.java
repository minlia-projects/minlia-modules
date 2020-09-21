package com.minlia.module.gad.code;

import com.minlia.cloud.code.Code;
import com.minlia.module.common.constant.MinliaConstants;

/**
 * Created by Garen On 2017/12/16.
 * API响应码
 *
 * @author garen
 */
public class GadCode {

    final static String CODE_PREFIX = MinliaConstants.APP_NAME + ".gad";

    public enum Message implements Code {

        /**
         * 高德地图 web api key 未配置
         */
        WEB_API_KEY_NOT_FOUND,

        /**
         * 高德云图 table id 未配置
         */
        WEB_TABLE_ID_NOT_FOUND,

        /**
         * 获取高德地理位置失败
         */
        REGEO_FAILURE;

        @Override
<<<<<<< HEAD
        public String module() {
            return CODE_PREFIX;
=======
        public String code() {
            return this.name();
        }

        @Override
        public String i18nKey() {
            return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, new StringJoiner(SymbolConstants.DOT)
                    .add(CODE_PREFIX)
                    .add(this.getClass().getSimpleName())
                    .add(this.name()).toString());
        }

        @Override
        public String message(Object... var1){
            return Lang.get(this.i18nKey(), var1);
>>>>>>> dev/garen
        }

    }

}