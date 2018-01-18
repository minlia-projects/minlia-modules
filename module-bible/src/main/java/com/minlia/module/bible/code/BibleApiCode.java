package com.minlia.module.bible.code;


import com.minlia.cloud.code.ApiCode;

/**
 * Created by will on 6/21/17.
 * API响应码
 */
public class BibleApiCode extends ApiCode {

  public BibleApiCode() {
    throw new AssertionError();
  }

  private static final String BASED_ON = "300000";

  /**
   * 定义模块代码基数为13000
   */
  public static final String MODULE_CODE_BASEDON = BASED_ON + 3000;

}
