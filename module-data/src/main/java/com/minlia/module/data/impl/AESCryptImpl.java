package com.minlia.module.data.impl;

import com.minlia.module.data.crypt.Crypt;
import com.minlia.module.data.util.AESUtils;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/5/30 4:49 PM
 */
public class AESCryptImpl implements Crypt {

    @Override
    public String encrypt(String plain) {
        return AESUtils.getInstance().encrypt(plain);
    }

    @Override
    public String decrypt(String cipher) {
        return AESUtils.getInstance().decrypt(cipher);
    }

}