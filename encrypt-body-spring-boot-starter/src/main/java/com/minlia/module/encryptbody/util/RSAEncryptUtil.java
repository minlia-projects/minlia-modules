package com.minlia.module.encryptbody.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by garen on 2018/7/18.
 */
@Slf4j
public class RSAEncryptUtil {

    public static final String KEY_ALGORITHM = "RSA";

    /**
     * RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024
     */
    public static final int KEY_SIZE = 1024;

    public static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKfxQMr/QZekhZur/SWXN32Bd4bnnj5AcQtXxMY3IpNdLz1sySaEzO+YsFWhWWvualApTP2MhhjsA3hGexc4g1XhEvKCXmSbtAu/tsYe+iBulufX+I2K5QN/A5yH8Dt5Cf+pxMMP+E6WHwTptuHEL7ywb9J0EPcbiArW5fciLXsQIDAQAB";
    public static String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMp/FAyv9Bl6SFm6v9JZc3fYF3hueePkBxC1fExjcik10vPWzJJoTM75iwVaFZa+5qUClM/YyGGOwDeEZ7FziDVeES8oJeZJu0C7+2xh76IG6W59f4jYrlA38DnIfwO3kJ/6nEww/4TpYfBOm24cQvvLBv0nQQ9xuICtbl9yItexAgMBAAECgYA/FAzr0tfII/ZrtSfR37l/aJoPEuL3YW3t/4rDxOqn+bNH7+5z4PekNcnfletJtRfl6QLwKAhrk2s/IAipF2MjJtsPY5ptFDa10pJR/DQ5SEAiZ83hzvsgyuLFjm+rAAmveAcr/XIBGWg7TQXK59Q8MpVMfXar2xx5WWqLxf+ZnQJBAOUtl/w4vDLWuTn8S+GkrJVhTzKg+8mdv6/2TIcXqqlxE6kQzqmjvms8hHUwnDb3fSMLObyhhPpE8mgTrwpVkt8CQQDiMhL0x1kuTqDMCtUHseeH6Piu5U1iKrAUxBzqqcDQKwQDtwDTQxCsTRAxVSbrlo7CcuDDOjceBqROAg4vDfdvAkBs3OKUWfL0B1GHPNRixBGDB+1R9GyGUhvLHyktBs33nRIkviodJP3//IhDDqs15QwZSGzNsL/1DilDzQ3Zz9prAkEAnCKgfyKT5qkTyYS4pAUjoucnseJKVjbNMKhmpXzjwU3QCZhrE2k5uxW+1a7HnNtiU8rkZx5qKWnARLCahdSINQJBAOBiIvDlIbcLvFxHc+1Nct7N/jtrrA02ITQzREKGAOZ9KEGv7cDtZxXA1TlQlt4VvN1REEX0cZH37NWuTRM9wYg=";

    public static String TEST_CONTENT = "{\"number\":\"A2019070413504418067\",\"cellphone\":\"41111111\",\"surname\":\"asdasdds\",\"givenName\":\"********\",\"gender\":\"MALE\",\"hkid\":\"********\",\"birthDate\":959788800000,\"mobilePhoneNumber\":41223232,\"residentialAddress\":\"2121\",\"residentialDistrict\":[\"2000\",\"2002\"],\"correspondentAddressFlag\":\"Y\",\"annualIncome\":111,\"correspondentAddress\":\"2121\",\"correspondentDistrict\":[\"2000\",\"2002\"]}";


    /**
     * 随机生成公钥和私钥
     */
    public static Map<String, Object> getKeyPair() throws NoSuchAlgorithmException {
        //获取密钥对生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //设置密钥长度
        keyPairGenerator.initialize(KEY_SIZE);
        //获取密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //获取公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //获取私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //公私钥对象存入map中
        Map<String, Object> keyMap = new HashMap(2);
        keyMap.put(PUBLIC_KEY, Base64Utils.encodeToString(publicKey.getEncoded()));//获取公钥Base64编码
        keyMap.put(PRIVATE_KEY, Base64Utils.encodeToString(privateKey.getEncoded()));//获取密钥Base64编码
        return keyMap;
    }

    /**
     * 通过字符串生成公钥
     */
    public static RSAPublicKey getPublicKey(String publicKeyData) {
        RSAPublicKey publicKey = null;
        try {
            byte[] keyBytes = Base64Utils.decodeFromString(publicKeyData); //将字符串Base64解码
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    /**
     * 通过字符串生成私钥
     */
    public static RSAPrivateKey getPrivateKey(String privateKeyData) {
        RSAPrivateKey privateKey = null;
        try {
            byte[] keyBytes = Base64Utils.decodeFromString(privateKeyData); //将字符串Base64解码
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);//创建x509证书封装类
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);//指定RSA
            privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);//生成私钥
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    /**
     * 加密
     *
     * @param bytes
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] bytes, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param bytes
     * @param privateKey
     * @return
     */
    public static byte[] decrypt(byte[] bytes, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encrypt(String content, String publicKey) {
        RSA rsa = new RSA(null, publicKey);
        return rsa.encryptBase64(content, KeyType.PublicKey);
    }

    public static String decrypt(String content, String privateKey) {
        RSA rsa = new RSA(privateKey, null);
        byte[] decrypt = rsa.decryptFromBase64(content, KeyType.PrivateKey);
        return StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8);
    }

    public static void main(String[] args) throws Exception {
        //随机生成公钥和私钥
//        Map<String, Object> map = getKeyPair();
//        System.out.println(map.get(PUBLIC_KEY).toString());
//        System.out.println(map.get(PRIVATE_KEY).toString());

//        System.out.println("排序前：" + content);
//        String jsonSorted = jsonSortToString(content, "&", "sign");
//        System.out.println("排序后：" + jsonSorted);


        String s = decrypt("h9pwaeCf/WvN1ohCK1fRvwoSD7tc0W5jiOToVwFT21iGaTSy4jMtNojTMUlyrKQDF6YY8PdZSya69XQwLoN37SR8oMrwpxdguuqL/HKXBc/aFbOasMFv9/Ju7kHFKisylc5m7r25INOyiXY4W+XyXF2B/Y9lHnCAnmObCwp8LxgF6oN3JawBZ/ccl68fFqYQW/1SiRIQDLKr9nlMmbokGSb84YugGhYXGygTeqk5oUzqN+pGhbHwVF+wE0fiiiKBLaY6DumEEyuQV/WO8pJCQG51uJIMcWdx2H8P2prrj06X6ABZWaXLFcw24dEaBCPijOXCXPaCYD4oLF9zZXJ7X1mKyKIHF2c5ACeZVNTADMLxYYftnu8QEaES7LnQYw/aVGyB6QEc8uMvD/jJkqMRMuLzd1cbQcfE+J+5f2GRz9yXtpZSco3vMoxr9MZVB2Sv0n9BcIlbvzG2PXoDmYF4ZzITnkbibNxjK55mJwX+0K77Jv+PO0KncifD0JS9Al1D",
                PRIVATE_KEY);
        System.out.println(s);


        RSA rsa = new RSA(PRIVATE_KEY, PUBLIC_KEY);
        //公钥加密，私钥解密
        byte[] encrypt = rsa.encrypt(StrUtil.bytes(TEST_CONTENT, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);
        System.out.println(StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));

        //私钥加密，公钥解密
        byte[] encrypt2 = rsa.encrypt(StrUtil.bytes(TEST_CONTENT, CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);
        System.out.println(StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8));
    }

//    public static String jsonSortToString(String jsonStr, String joiner, String... ignores) {
//        Gson gson = new Gson();
//        Map<String, Integer> map = gson.fromJson(jsonStr,Map.class);
////        map.keySet().forEach(System.out::println);
//        StringJoiner sj = new StringJoiner(joiner);
//        List list = Arrays.asList(ignores);
//        map.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(x -> {
//            if (CollectionUtils.is(list) && !list.contains(x.getKey())) {
//                sj.add(x.toString());
//            }
//        });
//        return sj.toString();
//    }

}
