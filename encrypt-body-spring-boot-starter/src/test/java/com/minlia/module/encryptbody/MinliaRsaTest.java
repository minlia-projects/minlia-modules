//package com.minlia.module.encryptbody;
//
//import com.alibaba.fastjson.JSON;
//import com.minlia.module.encryptbody.util.MinliaRsa;
//import junit.framework.Test;
//import junit.framework.TestCase;
//import junit.framework.TestSuite;
//import org.apache.commons.io.FileUtils;
//import org.springframework.core.io.ClassPathResource;
//
//import java.io.IOException;
//import java.util.Map;
//
//public class MinliaRsaTest extends TestCase {
//    public MinliaRsaTest(String testName) {
//        super(testName);
//    }
//
//    public static Test suite() {
//        return new TestSuite(MinliaRsaTest.class);
//    }
//
//
//    public static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKfxQMr/QZekhZur/SWXN32Bd4bnnj5AcQtXxMY3IpNdLz1sySaEzO+YsFWhWWvualApTP2MhhjsA3hGexc4g1XhEvKCXmSbtAu/tsYe+iBulufX+I2K5QN/A5yH8Dt5Cf+pxMMP+E6WHwTptuHEL7ywb9J0EPcbiArW5fciLXsQIDAQAB";
//    public static String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMp/FAyv9Bl6SFm6v9JZc3fYF3hueePkBxC1fExjcik10vPWzJJoTM75iwVaFZa+5qUClM/YyGGOwDeEZ7FziDVeES8oJeZJu0C7+2xh76IG6W59f4jYrlA38DnIfwO3kJ/6nEww/4TpYfBOm24cQvvLBv0nQQ9xuICtbl9yItexAgMBAAECgYA/FAzr0tfII/ZrtSfR37l/aJoPEuL3YW3t/4rDxOqn+bNH7+5z4PekNcnfletJtRfl6QLwKAhrk2s/IAipF2MjJtsPY5ptFDa10pJR/DQ5SEAiZ83hzvsgyuLFjm+rAAmveAcr/XIBGWg7TQXK59Q8MpVMfXar2xx5WWqLxf+ZnQJBAOUtl/w4vDLWuTn8S+GkrJVhTzKg+8mdv6/2TIcXqqlxE6kQzqmjvms8hHUwnDb3fSMLObyhhPpE8mgTrwpVkt8CQQDiMhL0x1kuTqDMCtUHseeH6Piu5U1iKrAUxBzqqcDQKwQDtwDTQxCsTRAxVSbrlo7CcuDDOjceBqROAg4vDfdvAkBs3OKUWfL0B1GHPNRixBGDB+1R9GyGUhvLHyktBs33nRIkviodJP3//IhDDqs15QwZSGzNsL/1DilDzQ3Zz9prAkEAnCKgfyKT5qkTyYS4pAUjoucnseJKVjbNMKhmpXzjwU3QCZhrE2k5uxW+1a7HnNtiU8rkZx5qKWnARLCahdSINQJBAOBiIvDlIbcLvFxHc+1Nct7N/jtrrA02ITQzREKGAOZ9KEGv7cDtZxXA1TlQlt4VvN1REEX0cZH37NWuTRM9wYg=";
//
//
//    public void testPublicEncryptPrivateDecrypt() {
//        System.out.println("aaaa");
//        Map<String, String> keys = MinliaRsa.createKeys(2048);
////        MinliaRsa rsa = new MinliaRsa(keys.get("publicKey"), keys.get("privateKey"));
//        MinliaRsa rsa = new MinliaRsa(PUBLIC_KEY, PRIVATE_KEY);
//        String data = "hello world";
//        try {
//            data = FileUtils.readFileToString( new ClassPathResource("rsa/test-data/img_base64.txt").getFile(), "UTF-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        String encrypted = rsa.publicEncrypt(data);
//        String decrypted = rsa.privateDecrypt(data);
//        assertEquals(data, decrypted);
//    }
//
//    public void testPrivateEncryptPublicDecrypt() {
//        Map<String, String> keys = MinliaRsa.createKeys(2048);
//        MinliaRsa rsa = new MinliaRsa(keys.get("publicKey"), keys.get("privateKey"));
//
//        String data = "";
//        try {
//            data = FileUtils.readFileToString( new ClassPathResource("rsa/test-data/img_base64.txt").getFile(), "UTF-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String encrypted = rsa.privateEncrypt(data);
//        System.out.println(encrypted);
//        String decrypted = rsa.publicDecrypt(encrypted);
//        System.out.println(decrypted);
//        assertEquals(data, decrypted);
//
//    }
//
//    public void testSign() {
//        Map<String, String> keys = MinliaRsa.createKeys(2048);
//        MinliaRsa rsa = new MinliaRsa(keys.get("publicKey"), keys.get("privateKey"));
//        String data = "hello world";
//
//        String sign = rsa.sign(data);
//        Boolean isValid = rsa.verify(data, sign);
//        assertTrue(isValid);
//    }
//
//    public void testCrossPlatform() {
//
//        String pubKey = null;
//        String priKey = null;
//        String dataStr = null;
//
//        try {
//            pubKey = FileUtils.readFileToString( new ClassPathResource("rsa/test-data/pub.base64cert").getFile(), "UTF-8");
//            priKey = FileUtils.readFileToString( new ClassPathResource("rsa/test-data/pri.base64cert").getFile()  , "UTF-8");
//            dataStr = FileUtils.readFileToString(new ClassPathResource("rsa/test-data/data.json").getFile() , "UTF-8");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Map<String, String> data = (Map) JSON.parse(dataStr);
//
//        MinliaRsa rsa = new MinliaRsa(pubKey, priKey);
//        String decrypted = rsa.privateDecrypt(data.get("encrypted"));
//
//        System.out.println(decrypted);
//        assertEquals(data.get("data"), decrypted);
//        decrypted = rsa.publicDecrypt(data.get("private_encrypted"));
//
//        System.out.println(decrypted);
//        assertEquals(data.get("data"), decrypted);
//
//        String sign = rsa.sign(data.get("data"));
//        Boolean isValid = rsa.verify(data.get("data"), sign);
//
//        assertTrue(isValid);
//    }
//
//}
//
