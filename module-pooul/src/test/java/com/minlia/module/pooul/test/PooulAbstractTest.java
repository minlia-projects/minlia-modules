package com.minlia.module.pooul.test;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.module.common.config.RestConfiguration;
import com.minlia.module.common.util.NumberGenerator;
import com.minlia.module.pooul.Application;
import com.minlia.module.pooul.bean.to.PooulMerchantBusinessTO;
import com.minlia.module.pooul.bean.to.PooulMerchantOwnerTO;
import com.minlia.module.pooul.bean.to.PooulMerchantPersonalCTO;
import com.minlia.module.pooul.bean.to.PooulWechatJsminipgTO;
import com.minlia.module.pooul.enumeration.PayTypeEnum;
import com.minlia.module.pooul.service.PooulAuthService;
import com.minlia.module.pooul.service.PooulMerchantService;
import com.minlia.module.pooul.service.PooulPayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
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
    private PooulPayService pooulPayService;

    @Autowired
    private PooulAuthService pooulAuthService;

    @Autowired
    private PooulMerchantService pooulMerchantService;

    @Test
    public void login(){
        String authorization = pooulAuthService.login();
        System.out.println(authorization);
    }

    @Test
    public void jsminipg() {
        PooulWechatJsminipgTO body = new PooulWechatJsminipgTO();
        body.setPayType(PayTypeEnum.wechat_jsminipg.getName());
        body.setNonceStr(NumberGenerator.uuid32());
        body.setMchTradeId("MO00002");
        body.setTotalFee(1);
        body.setBody("花果山 Test jsminipg");
        body.setSubAppid("wx469ffdb81de47e4d");
        body.setSubOpenid("oerQA5Q5clTAK8eA3tGNOAiz7s4o");
        StatefulBody statefulBody = pooulPayService.wechatJsminipg(body);
        System.out.println(statefulBody.getPayload());
    }

    @Test
    public void closeOrder() {
        pooulPayService.close("O2018082215595946327");
    }

    @Test
    public void createMerchant(){
        PooulMerchantPersonalCTO cto = PooulMerchantPersonalCTO.builder()
                .note("测试")
                .business(PooulMerchantBusinessTO.builder().short_name("测试").build())
                .owner(PooulMerchantOwnerTO.builder().idcard_type("1").name("测试").build())
                .build();
        pooulMerchantService.create(cto);
    }

    @Test
    public void deleteMerchant() {
        Response response = pooulMerchantService.delete("5551783456268768");
        System.out.println(response.isSuccess());
    }

}
