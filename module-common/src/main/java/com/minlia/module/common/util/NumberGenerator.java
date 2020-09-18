package com.minlia.module.common.util;

import com.minlia.cloud.constant.SymbolConstants;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * Created by garen on 2017/10/23.
 */
public class NumberGenerator {

    public static String generator(String prefix, Integer suffix) {
        String generated = prefix + String.format("%02d", suffix);
        return generated;
    }

    public static String generatorByHMSS(String prefix, Integer stuffLength) {
        return prefix + RandomStringUtils.randomNumeric(stuffLength) + LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddHHmmssSSS"));
    }

    public static String generatorByYMDHMS(String prefix, Integer stuffLength) {
        return prefix + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + RandomStringUtils.randomNumeric(stuffLength);
    }

    public static String generatorByYMDHMSS(String prefix, Integer stuffLength) {
        return prefix + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + RandomStringUtils.randomNumeric(stuffLength);
    }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
    }

    public static String generatorByTimestamp(String prefix, Integer stuffLength) {
        String generated = prefix + System.currentTimeMillis() + RandomStringUtils.randomNumeric(stuffLength);
        return generated;
    }

    public static String uuid32() {
        String generated = UUID.randomUUID().toString().replace("-", "");
        return generated;
    }

    public static String generatorAlphanumeric(int count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }

    /**
     * 激活码 V7BP-C6H9-JFJ6-6FPX
     *
     * @return
     */
    public static String activationCode() {
        return activationCode(4, 4);
    }

    /**
     * 激活码 V7BP-C6H9-JFJ6-6FPX
     *
     * @return
     */
    public static String activationCode(int length, int times) {
        StringJoiner sj = new StringJoiner(SymbolConstants.ZHX);
        for (int i = 0; i < times; i++) {
            sj.add(RandomStringUtils.randomAlphanumeric(length));
        }
        return StringUtils.upperCase(sj.toString());
    }

}
