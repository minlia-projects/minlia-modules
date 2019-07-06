package com.minlia.module.encryptbody;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/7/5 7:33 PM
 */
public class EncFileTest {

    private static final int numOfEncAndDec = 0x88; //加密解密秘钥
    private static int dataOfFile = 0; //文件字节内容

    @Test
    public void EncFile() throws Exception {
        File file = new File("rsa/test-data/xorImg11.txt");
        if (!file.exists()) {
            System.out.println("source file not exixt");
            file.createNewFile();
            return;
        }
        System.out.println(file.getPath());
        System.out.println(file.getAbsolutePath());

//        if (!encFile.exists()) {
//            System.out.println("encrypt file created");
//            encFile.createNewFile();
//        }
        InputStream fis = new FileInputStream(new ClassPathResource("rsa/test-data/xorImg.txt").getFile());
        OutputStream fos = new FileOutputStream(new ClassPathResource("rsa/test-data/xorImg1.txt").getFile());

        while ((dataOfFile = fis.read()) > -1) {
            fos.write(dataOfFile ^ numOfEncAndDec);
        }

        fis.close();
        fos.flush();
        fos.close();
    }

    @Test
    public void DecFile() throws Exception {


        System.out.println(numOfEncAndDec);
//        int ii = Integer.parseInt("Ox88");
//        System.out.println(ii);


        String data = "hello world";
        try {
            data = FileUtils.readFileToString( new ClassPathResource("rsa/test-data/xorImg.txt").getFile(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            sb.append( (char) (136 ^ data.charAt(i)));
        }

        System.out.println(sb.toString());
    }

}