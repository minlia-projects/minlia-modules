package com.minlia.modules.otp.starter;

import com.minlia.modules.otp.sms.OtpSmsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AliyunSmsServiceTest {

    @Autowired
    private OtpSmsService otpSmsService;

    @Test
    public void send() {
        otpSmsService.send("18566297716", "SMS_56155265", "{\"name\":\"" + "天上有个林妹妹" + "\"}");
    }
}
