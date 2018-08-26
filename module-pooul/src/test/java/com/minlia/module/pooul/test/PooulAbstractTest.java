package com.minlia.module.pooul.test;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.module.common.config.RestConfiguration;
import com.minlia.module.common.util.NumberGenerator;
import com.minlia.module.pooul.Application;
import com.minlia.module.pooul.bean.to.PooulWechatJsminipgTO;
import com.minlia.module.pooul.enumeration.PayTypeEnum;
import com.minlia.module.pooul.service.PooulPayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by will on 9/10/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RestConfiguration.class,Application.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PooulAbstractTest {

//    @Before
//    public void setUp() throws Exception {
//    }

    /**
     * @LocalServerPort 提供了 @Value("${local.server.port}") 的代替
     */
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PooulPayService pooulPayService;

    @Test
    public void jsminipg() {
        pooulPayService.close("O2018082215595946327");

//        PooulWechatJsminipgTO body = new PooulWechatJsminipgTO();
//        body.setPayType(PayTypeEnum.wechat_jsminipg.getName());
//        body.setNonceStr(NumberGenerator.uuid32());
//        body.setMchTradeId("MO00002");
//        body.setTotalFee(1);
//        body.setBody("花果山 Test jsminipg");
//        body.setSubAppid("wx469ffdb81de47e4d");
//        body.setSubOpenid("oerQA5Q5clTAK8eA3tGNOAiz7s4o");
//        StatefulBody statefulBody = pooulPayService.wechatJsminipg(body);
//        System.out.println(statefulBody.getPayload());
    }

}
