package com.minlia.module.bank1.test;

import com.minlia.module.bank1.Application;
import com.minlia.module.bank1.service.BankcodeService;
import com.minlia.module.common.config.RestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by will on 9/10/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RestConfiguration.class,Application.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankTest {

//    @Before
//    public void setUp() throws Exception {
//    }

    /**
     * @LocalServerPort 提供了 @Value("${local.server.port}") 的代替
     */
    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BankcodeService bankcodeService;



    @Test
    public void lhhInit() {
        String host = "http://lhh.market.alicloudapi.com";
        String path = "/lhh";
        String method = "GET";
        String appcode = "6889a6bedf53468ea27d10f12a8e5159";

        Map<String, String> querys = new HashMap<String, String>();
//        querys.put("bankcard", "bankcard");
//        querys.put("bankname", "bankname");
//        querys.put("city", "city");
//        querys.put("district", "district");
//        querys.put("keyword", "keyword");
//        querys.put("page", "page");
//        querys.put("province", "province");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "APPCODE " + appcode);
        HttpEntity httpEntity = new HttpEntity(querys,headers);
        String response = restTemplate.getForObject("http://lhh.market.alicloudapi.com/lhh",String.class,httpEntity);

        System.out.println(response);

//        bankcodeService.create();
    }

}
