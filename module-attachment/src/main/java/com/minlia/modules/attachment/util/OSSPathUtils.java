package com.minlia.modules.attachment.util;

import com.minlia.module.common.constant.SymbolConstants;
import com.minlia.modules.attachment.constant.AttachmentConstant;
import com.minlia.modules.qcloud.oss.builder.Constant;

import java.util.Date;
import java.util.UUID;

import static org.apache.commons.lang3.time.DateFormatUtils.ISO_DATE_FORMAT;

/**
 * Created by garen on 2018/6/6.
 */
public class OSSPathUtils {

    public static String dateBuild() {
        return ISO_DATE_FORMAT.format(new Date()) + SymbolConstants.LEFT_LINE;
    }

    public static String uuidNameBuild(String fileName) {
        String suffix = SymbolConstants.EMPTY;

        //如果文件名有"."的话，进入 ，否则不进入
        if (fileName.contains(Constant.DOT)) {
            //剔除.jpg前的字符串
            suffix = fileName.substring(fileName.lastIndexOf(SymbolConstants.DOT));
        }
        return UUID.randomUUID().toString() + suffix;    //“wsedrftgyhnjmk.jpg”
    }

    /**
     * 默认文件路径  others/2018-06-06/uuid.jps
     * @param fileName
     * @return
     */
    public static String defaultBuild(String fileName) {
        return AttachmentConstant.DEFAULT_PATH + dateBuild() + uuidNameBuild(fileName);
    }

}
