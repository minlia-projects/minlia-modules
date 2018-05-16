//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.minlia.module.unified.payment.util;

import java.util.Date;

import com.minlia.cloud.utils.DateTimeUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class OrderNumberUtil {
  public static final String DEFAULT_ORDER_NUMBER_PREFIX = "O";
  public static final String REFUND_NUMBER_PREFIX = "R";

  public OrderNumberUtil() {
  }

  public static String generateOrderNumberYearMonthDay() {
    return generateOrderNumberYearMonthDay("");
  }

  public static String generateOrderNumberYearMonthDay(String prefix) {
    String current = DateTimeUtil.formatDateTimetoString(new Date(), "yyyyMMddHHmmss");
    return StringUtils.isNotEmpty(prefix)?prefix + current + RandomStringUtils.randomNumeric(6):current + RandomStringUtils.randomNumeric(6);
  }

  public static String generateOrderNumberTimestamp(String prefix) {
    String generated = String.valueOf(System.currentTimeMillis());
    if(StringUtils.isNotEmpty(prefix)) {
      generated = prefix + generated;
    }

    return generated;
  }

  public static String generateRefundNumberTimestamp(String prefix) {
    String generated = String.valueOf(System.currentTimeMillis());
    if(StringUtils.isNotEmpty(prefix)) {
      generated = "R" + prefix + generated;
    }

    return generated;
  }

  public static String generateOrderNumberTimestamp() {
    return generateOrderNumberTimestamp("O");
  }
}
