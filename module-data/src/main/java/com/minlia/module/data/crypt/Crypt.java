package com.minlia.module.data.crypt;

/**
 * 脱敏算法接口定义
 *
 * @author wangzhuhua
 * @date 2018/09/04 下午5:34
 **/
public interface Crypt {

    /**
     * 加密
     *
     * @param plain 原始明文
     * @return 密文
     */
    String encrypt(String plain);

    /**
     * 解密
     *
     * @param cipher 密文
     * @return 原始明文
     */
    String decrypt(String cipher);

}