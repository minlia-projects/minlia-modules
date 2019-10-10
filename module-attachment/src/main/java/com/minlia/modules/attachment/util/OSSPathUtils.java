package com.minlia.modules.attachment.util;

import com.minlia.module.common.constant.SymbolConstants;
import com.minlia.modules.attachment.constant.AttachmentConstant;
import com.minlia.modules.qcloud.oss.builder.Constant;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


/**
 * Created by garen on 2018/6/6.
 */
public class OSSPathUtils {

    public static String dateBuild() {
        return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + SymbolConstants.LEFT_LINE;
    }

    /**
     * 获取文件路径
     * @param relationId
     * @param belongsTo
     * @return
     */
    public static String getPath(String fileName, String relationId, String belongsTo) {
        return getDirectory(relationId, belongsTo) + getUuidFileName(fileName);
    }

    /**
     * 默认文件路径  others/2018-06-06/uuid.jps
     * @param fileName
     * @return
     */
    public static String getDefaultPath(String fileName) {
        return getPath(fileName,null, null);
    }

    /**
     * 获取文件目录
     * @param relationId
     * @param belongsTo
     * @return
     */
    public static String getDirectory(String relationId, String belongsTo) {
        String path;
        if (StringUtils.isNotBlank(belongsTo)) {
            path = dateBuild() + String.format("%s/%s", belongsTo, relationId);
        } else {
            path = dateBuild() + AttachmentConstant.DEFAULT_PATH;
        }
        return path;
    }

    /**
     * UUID文件名
     * @param fileName
     * @return
     */
    public static String getUuidFileName(String fileName) {
        String suffix = SymbolConstants.EMPTY;
        //如果文件名有"."的话，进入 ，否则不进入
        if (fileName.contains(Constant.DOT)) {
            //剔除.jpg前的字符串
            suffix = fileName.substring(fileName.lastIndexOf(SymbolConstants.DOT));
        }
        return UUID.randomUUID().toString() + suffix;    //“wsedrftgyhnjmk.jpg”
    }

}
