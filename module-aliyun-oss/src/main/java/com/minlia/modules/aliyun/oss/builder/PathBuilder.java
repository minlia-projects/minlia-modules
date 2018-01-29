package com.minlia.modules.aliyun.oss.builder;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.UUID;

/**
 * Created by garen on 2017/12/22.
 */
public class PathBuilder {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd";

    public static String dateBuild() {
        return DateFormatUtils.format(new Date(),DEFAULT_PATTERN) + Constant.SLASH;
    }

    public static String uuidNameBuild(String fileName) {
        String suffix = Constant.EMPTY;  //设置一个常量值

        //Constant.DOT:"."     contains()方法返回true,当且仅当此字符串包含指定的char值序列

        //如果文件名有"."的话，进入 ，否则不进入
        if (fileName.contains(Constant.DOT)) {
            //lastIndexOf() 方法可返回一个指定的字符串值最后出现的位置，在一个字符串中的指定位置从后向前搜索。
            //剔除.jpg前的字符串
            suffix = fileName.substring(fileName.lastIndexOf(Constant.DOT));
        }
        return UUID.randomUUID().toString() + suffix;    //“wsedrftgyhnjmk.jpg”
    }

}
