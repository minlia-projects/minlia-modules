package com.minlia.module.wechat.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;  
import javax.crypto.spec.SecretKeySpec;

import me.chanjar.weixin.common.util.crypto.PKCS7Encoder;
import sun.misc.BASE64Decoder;
import com.alibaba.fastjson.JSONObject;

import java.nio.charset.Charset;
import java.util.Arrays;

/** 
 * 对微信小程序用户加密数据的解密示例代码. 
 *  
 * @ClassName WXBizDataCrypt 
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @author tf 
 * @Date 2016年11月19日 下午2:56:36 
 * @version 1.0.0 
 */  
@SuppressWarnings("restriction")  
public class WXBizDataCrypt {  
    /* 
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。 
     */  
    private static WXBizDataCrypt instance = null;  
  
    private WXBizDataCrypt() {  
  
    }  
  
    public static WXBizDataCrypt getInstance() {  
        if (instance == null)  
            instance = new WXBizDataCrypt();  
        return instance;  
    }  
  
    /** 
     * 对于官方加密数据（encryptData）解密说明如下： 加密数据解密算法 接口如果涉及敏感数据（如wx.getUserInfo当中的 
     * openId 和unionId ），接口的明文内容将不包含这些敏感数据。开发者如需要获取敏感数据，需要对接口返回的加密数据( 
     * encryptedData )进行对称解密。 解密算法如下： 对称解密使用的算法为 AES-128-CBC，数据采用PKCS#7填充。 
     * 对称解密的目标密文为 Base64_Decode(encryptedData), 对称解密秘钥 aeskey = 
     * Base64_Decode(session_key), aeskey 是16字节 对称解密算法初始向量 iv 会在数据接口中返回。 
     *  
     * @Description (TODO这里用一句话描述这个方法的作用) 
     * @param encryptedData 
     *            加密内容 
     * @param sessionKey 
     *            小程序登录sessionKey 
     * @param iv 
     *            解密算法初始向量 iv 会在数据接口中返回。 
     * @param encodingFormat 
     *            编码格式默认UTF-8 
     * @return 返回解密后的字符串 
     * @throws Exception 
     */  
    public String decrypt(String encryptedData, String sessionKey, String iv, String encodingFormat) throws Exception {  
        try {  
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");  
            BASE64Decoder base64Decoder = new BASE64Decoder();  
            byte[] _encryptedData = base64Decoder.decodeBuffer(encryptedData);
            byte[] _sessionKey = base64Decoder.decodeBuffer(sessionKey);
            byte[] _iv = base64Decoder.decodeBuffer(iv);

            SecretKeySpec secretKeySpec = new SecretKeySpec(_sessionKey, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(_iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] original = cipher.doFinal(_encryptedData);
            byte[] bytes = PKCS7Encoder.decode(original);
            String originalString = new String(bytes, "UTF-8");

            decode(original);

            return originalString;
        } catch (Exception ex) {
            return null;  
        }
    }

    private byte[] decode(byte[] decrypted) {
        int pad = decrypted[decrypted.length - 1];
        if (pad < 1 || pad > 32) {
            pad = 0;
        }
//        return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);


        byte[] bytes = Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);

        System.out.println(new String(bytes, Charset.forName("UTF-8")));

        return null;
    }
  
    public static void main(String[] args) throws Exception {  
//        String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
//        String iv = "r7BXXKkLb8qrSNn05n0qiA==";
//        String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZM" + "QmRzooG2xrDcvSnxIMXFufNstNGTyaGS"
//                + "9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+" + "3hVbJSRgv+4lGOETKUQz6OYStslQ142d"
//                + "NCuabNPGBzlooOmB231qMM85d2/fV6Ch" + "evvXvQP8Hkue1poOFtnEtpyxVLW1zAo6"
//                + "/1Xx1COxFvrc2d7UL/lmHInNlxuacJXw" + "u0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn"
//                + "/Hz7saL8xz+W//FRAUid1OksQaQx4CMs" + "8LOddcQhULW4ucetDf96JcR3g0gfRK4P"
//                + "C7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB" + "6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns"
//                + "/8wR2SiRS7MNACwTyrGvt9ts8p12PKFd" + "lqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYV"
//                + "oKlaRv85IfVunYzO0IKXsyl7JCUjCpoG" + "20f0a04COwfneQAGGwd5oa+T8yO5hzuy" + "Db/XcxxmK01EpqOyuxINew==";


//        String sessionKey = "B/Gf+p2fo0qi5FDP4uhb1A==";
//        String iv = "Ch3AhAc6qmLfXDr/WghF/g==";
//        String encryptedData = "rq9Yw50Z6VdrM5RRGOkBuNlnNVCd6vlTtOxYE7tdpDOj7x4AgzFTMWzyLRAm/el8s8tjygTbpYCgNjCzT7frZ7dt2RMS/0xmd1xal/SiV4NBWrTqOuFI6WPP3a6HcH3BvzxJzKHzTBbkWR+k+ZYiFrAbq0fpQPw4e800P8VGHcPP95NtX6j25+P50POXx+aoiCw0wP70ChD5SFJTkqy6UwdfOEUcLE88EYAlBLCJIRz/XqufC9zf01f+V5qowcW5WVPq3WWZeMSO1XdkP2ZqaFNu/P1URq49LdlzxLmUu6HDlpqZMqjMIav8K2xZNEDPVxgsF+B0tcUwyT3YZB7GD5RdZ+8YyDUOhZ1+PQnrQPeIUhvvQxmDkMWMuVrW13qeNe6+z/zQnGABi25QwPsFGyv4HQy6ZfJ6NFyLEoy8N05fvBVE2Pm3MieSJhZfg5hOi9Vo9dIHPfIkePhZcaIpx8KznRj04V+quvPuzS54fyM=";

//        String sessionKey = "N1Dp52POpgOb6GxeDMFESA==";
//        String iv = "A8Z4eMh62zzlUzPVs/SoeQ==";
//        String encryptedData = "yww05k+qeiyqj0NDd4AP+K3yc0CcFjBdzRpotMOI6q656qfarf7k/DA4z/LNWmmUlMnUKyOTbZefrClK/0xRkJH31eXCUfaxVVj1owHM11riDoiCz494eEofXWmSvFVPUwPKIO6YtXXNHxvEe6jbswZ2zpT3EqwSYLUb9jGb6u2McP3H6/OgQEGrcwPOXXLGw9/EFz0OXNIlLNSes15M1vjXUGAp7LxkAnFer5Mr59ok38/32ekUC5pOOVtD7ZbFMnT2+xny1mN0c8eCgtCEjBaZl5f6haFod4jGWjhzNF2uM8ZmWpgpK5U1iNcxeyiW3F/TKgKEZS5AGDdpsheshiUL0lAPS1GEkgE2bO6l1lA+JaJrPh7K6VeR8eHvzgmSS+NAitPSVTLPo0P5xxVfP4LBv7AKdYe8PEKMajbyYUF2Vl8N05SsU/RDp5DuohCa0jwgudgEfDI4RpOD3kXntXsX9LlsoSVqz2PgF0ZYug0=";

        String sessionKey = "5F+y9llL8g+Qq7Nj9qwhtg==";
        String iv = "13ui49mxn/XHH4K9ydIY2g==";
        String encryptedData = "0JdAqJqz4YtWUB6EKwRCvrmd4yHqdAoJdnjrRbpRnEVwedz605RMmYwOM4rmSpGSlNmex0cDCqp71rPSTtHzp5tglBzVl0Qoq9T5Zwg3oymDFlpTRyUNhYy2xuS2bH7I2hchm9+AHPHNGLqpFSXeWIiLvsLLhPca3ltSnXkX+rKnrZF8dElVNwhy2OqSlFTubLSx15Hs9ld7G8mDwB6BkoXsBZWUywWHF11fqJZaOUXwTzQpnY5UedaqsUWG4YhdJN31ppFGPoOcLqG1PbG/QHXeVhPIAmVRMjnZDlgrPNXOG5cq2RRDwqWV/aKBG0vK0BmD7D+MlqH2Jt4qGMNAbgrKjMydAYuviXNrbaKDY5VD3fuccTCAi/s2gWfQm+3ZliII8jlo/6ADpBc2UiRrMLrevi1cladoAvwYTYAF0YD5+rItuMgSz3VBc2ESjCrZ8yQQ7xRyHwC3yhCMQiWzng==";

        String deString = WXBizDataCrypt.getInstance().decrypt(encryptedData, sessionKey, iv, "utf-8");
        JSONObject jsonObject = JSONObject.parseObject(deString);
        System.out.println(jsonObject);
    }


//    {
//        "encryptedData":"rq9Yw50Z6VdrM5RRGOkBuNlnNVCd6vlTtOxYE7tdpDOj7x4AgzFTMWzyLRAm/el8s8tjygTbpYCgNjCzT7frZ7dt2RMS/0xmd1xal/SiV4NBWrTqOuFI6WPP3a6HcH3BvzxJzKHzTBbkWR+k+ZYiFrAbq0fpQPw4e800P8VGHcPP95NtX6j25+P50POXx+aoiCw0wP70ChD5SFJTkqy6UwdfOEUcLE88EYAlBLCJIRz/XqufC9zf01f+V5qowcW5WVPq3WWZeMSO1XdkP2ZqaFNu/P1URq49LdlzxLmUu6HDlpqZMqjMIav8K2xZNEDPVxgsF+B0tcUwyT3YZB7GD5RdZ+8YyDUOhZ1+PQnrQPeIUhvvQxmDkMWMuVrW13qeNe6+z/zQnGABi25QwPsFGyv4HQy6ZfJ6NFyLEoy8N05fvBVE2Pm3MieSJhZfg5hOi9Vo9dIHPfIkePhZcaIpx8KznRj04V+quvPuzS54fyM=",
//        "iv":"Ch3AhAc6qmLfXDr/WghF/g==",
//        "code":"081Raywi2zsPEB0pZ3wi20Sowi2RaywT"
//    }

}  