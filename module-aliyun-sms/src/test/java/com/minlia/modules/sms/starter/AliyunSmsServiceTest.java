package com.minlia.modules.sms.starter;

import com.minlia.modules.sms.SmsSendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootContextLoader(classes = AliyunSmsTestApplication.class)

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(AliyunSmsTestApplication.class)
//@WebIntegrationTest
@ContextConfiguration(classes = AliyunSmsTestApplication.class)
public class AliyunSmsServiceTest {

    @Autowired
    private SmsSendService smsSendService;

    @Test
    public void send() {
        smsSendService.send("18566297716", "SMS_56155265", "{\"name\":\"" + "天上有个林妹妹" + "\"}");
    }
}
