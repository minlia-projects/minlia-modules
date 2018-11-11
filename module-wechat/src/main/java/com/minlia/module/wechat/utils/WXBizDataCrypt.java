package com.minlia.module.wechat.utils;

import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 微信小程序
 * 数据解密算法
 *
 * @author zhonghongqiang
 *
 */

public class WXBizDataCrypt {

    public WXBizDataCrypt(String appId, String iv, String sessionKey, String appId1) {
    }

//    private String appId;
//
//    private String sessionKey;
//
//    public WXBizDataCrypt(String appId, String sessionKey) {
//        this.appId = appId;
//        this.sessionKey = sessionKey;
//    }

    /**
     * 1.对称解密使用的算法为 WXBizDataCrypt-128-CBC，数据采用PKCS#7填充。
     * 2.对称解密的目标密文为 Base64_Decode(encryptedData)。
     * 3.对称解密秘钥 aeskey = Base64_Decode(session_key), aeskey 是16字节。
     * 4.对称解密算法初始向量 为Base64_Decode(iv)，其中iv由数据接口返回。
     * @param
     * @throws Exception
     */
    public static JSONObject decrypt(String encryptedData, String iv, String sessionKey, String appId) {
        String jsonStr = new String("");
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            /**
             * 小程序加密数据解密算法
             * https://developers.weixin.qq.com/miniprogram/dev/api/signature.html#wxchecksessionobject
             * 1.对称解密的目标密文为 Base64_Decode(encryptedData)。
             * 2.对称解密秘钥 aeskey = Base64_Decode(session_key), aeskey 是16字节。
             * 3.对称解密算法初始向量 为Base64_Decode(iv)，其中iv由数据接口返回。
             */
            byte[] encryptedByte = base64Decoder.decodeBuffer(encryptedData);
            byte[] sessionKeyByte = base64Decoder.decodeBuffer(sessionKey);
            byte[] ivByte = base64Decoder.decodeBuffer(iv);
            /**
             * 以下为AES-128-CBC解密算法
             */
            SecretKeySpec skeySpec = new SecretKeySpec(sessionKeyByte, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivByte);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
            byte[] original = cipher.doFinal(encryptedByte);
            jsonStr = new String(original);
        } catch (Exception ex) {
            throw new RuntimeException("AES解密失败", ex);
//            throw new Exception("Illegal Buffer");
        }

        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        if (!jsonObject.getJSONObject(Constants.WEAPP_WATERMARK).get(Constants.WEAPP_APPID).toString().equals(appId)){
            throw new RuntimeException("AES解密失败");
        }
        return jsonObject;
    }
}
