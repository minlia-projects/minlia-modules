package com.minlia.module.common.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * Created by garen on 2017/10/23.
 */
public class NumberGenerator {

    public static String generator(String prefix,Integer suffix){
        String generated=prefix + String.format("%02d", suffix);
        return generated;
    }

    public static String generatorByHMSS(String prefix,Integer stuffLength){
        return prefix + RandomStringUtils.randomNumeric(stuffLength) + DateFormatUtils.format(new Date(),"ddHHmmssSSS");
    }

    public static String generatorByYMDHMS(String prefix,Integer stuffLength){
        return prefix + DateFormatUtils.format(new Date(),"yyyyMMddHHmmss") + RandomStringUtils.randomNumeric(stuffLength);
    }

    public static String generatorByYMDHMSS(String prefix,Integer stuffLength){
        return prefix + DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS") + RandomStringUtils.randomNumeric(stuffLength);
    }

    public static String generatorByTimestamp(String prefix,Integer stuffLength) {
        String generated = prefix + System.currentTimeMillis() + RandomStringUtils.randomNumeric(stuffLength);
        return generated;
    }

}
