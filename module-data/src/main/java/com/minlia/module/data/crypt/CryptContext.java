package com.minlia.module.data.crypt;

import com.minlia.module.data.enumetation.CryptTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 脱敏方式上下文
 *
 * @author wangzhuhua
 * @date 2018/09/04 下午5:45
 **/
public class CryptContext {

    private static Map<CryptTypeEnum, Crypt> Crypts = new HashMap<>(CryptTypeEnum.values().length);

    /**
     * 获取加密方式
     *
     * @param cryptTypeEnum 加密方式枚举
     * @return 机密方式实现类
     */
    public static Crypt getCrypt(CryptTypeEnum cryptTypeEnum) {
        Crypt crypt = Crypts.get(cryptTypeEnum);
        if (crypt == null) {
            crypt = Crypts.get(CryptTypeEnum.AES);
        }
        return crypt;
    }

    /**
     * 设置加密方式
     *
     * @param cryptTypeEnum 加密类型
     * @param crypt         加载方式
     */
    public static void setCrypt(CryptTypeEnum cryptTypeEnum, Crypt crypt) {
        Crypts.put(cryptTypeEnum, crypt);
    }

}