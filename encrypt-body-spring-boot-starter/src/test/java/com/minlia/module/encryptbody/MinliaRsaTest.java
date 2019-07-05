package com.minlia.module.encryptbody;

import com.alibaba.fastjson.JSON;
import com.minlia.module.encryptbody.util.MinliaRsa;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Map;

public class MinliaRsaTest extends TestCase {
    public MinliaRsaTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(MinliaRsaTest.class);
    }

    public void testPublicEncryptPrivateDecrypt() {
        Map<String, String> keys = MinliaRsa.createKeys(2048);
        MinliaRsa rsa = new MinliaRsa(keys.get("publicKey"), keys.get("privateKey"));
        String data = "hello world";
        try {
            data = FileUtils.readFileToString( new ClassPathResource("rsa/test-data/img_base64.txt").getFile(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String encrypted = rsa.publicEncrypt(data);


        System.out.println(encrypted);
        String decrypted = rsa.privateDecrypt(encrypted);
        assertEquals(data, decrypted);
    }

    public void testPrivateEncryptPublicDecrypt() {
        Map<String, String> keys = MinliaRsa.createKeys(2048);
        MinliaRsa rsa = new MinliaRsa(keys.get("publicKey"), keys.get("privateKey"));

        String data = "";
        try {
            data = FileUtils.readFileToString( new ClassPathResource("rsa/test-data/img_base64.txt").getFile(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String encrypted = rsa.privateEncrypt(data);
        System.out.println(encrypted);
        String decrypted = rsa.publicDecrypt(encrypted);
        System.out.println(decrypted);
        assertEquals(data, decrypted);

    }

    public void testSign() {
        Map<String, String> keys = MinliaRsa.createKeys(2048);
        MinliaRsa rsa = new MinliaRsa(keys.get("publicKey"), keys.get("privateKey"));
        String data = "hello world";

        String sign = rsa.sign(data);
        Boolean isValid = rsa.verify(data, sign);
        assertTrue(isValid);
    }

    public void testCrossPlatform() {

        String pubKey = null;
        String priKey = null;
        String dataStr = null;

        try {
            pubKey = FileUtils.readFileToString( new ClassPathResource("rsa/test-data/pub.base64cert").getFile(), "UTF-8");
            priKey = FileUtils.readFileToString( new ClassPathResource("rsa/test-data/pri.base64cert").getFile()  , "UTF-8");
            dataStr = FileUtils.readFileToString(new ClassPathResource("rsa/test-data/data.json").getFile() , "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, String> data = (Map) JSON.parse(dataStr);

        MinliaRsa rsa = new MinliaRsa(pubKey, priKey);
        String decrypted = rsa.privateDecrypt(data.get("encrypted"));

        System.out.println(decrypted);
        assertEquals(data.get("data"), decrypted);
        decrypted = rsa.publicDecrypt(data.get("private_encrypted"));

        System.out.println(decrypted);
        assertEquals(data.get("data"), decrypted);

        String sign = rsa.sign(data.get("data"));
        Boolean isValid = rsa.verify(data.get("data"), sign);

        assertTrue(isValid);
    }

}

