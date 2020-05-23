package com.minlia.module.pooul.test;

import com.minlia.cloud.body.Response;
import com.minlia.module.common.config.RestConfiguration;
import com.minlia.module.common.util.NumberGenerator;
import com.minlia.module.pooul.Application;
import com.minlia.module.pooul.bean.dto.PooulBlancesDTO;
import com.minlia.module.pooul.bean.to.*;
import com.minlia.module.pooul.enumeration.PayTypeEnum;
import com.minlia.module.pooul.service.PooulAuthService;
import com.minlia.module.pooul.service.PooulBalancesService;
import com.minlia.module.pooul.service.PooulMerchantService;
import com.minlia.module.pooul.service.PooulPayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by will on 9/10/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RestConfiguration.class, Application.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

    @Autowired
    private PooulBalancesService pooulBalancesService;

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
        Response response = pooulPayService.wechatJsminipg(body, "2849928048545130");
        System.out.println(response.getPayload());
    }

    @Test
    public void closeOrder() {
        pooulPayService.close("O2018082215595946327");
    }

    @Test
    public void createMerchant(){
        PooulMerchantPersonalCTO cto = PooulMerchantPersonalCTO.builder()
                .note("测试")
                .business(PooulMerchantBusinessTO.builder().short_name("黄博").build())
                .owner(PooulMerchantOwnerTO.builder().idcard_type("1").name("黄博").build())
                .build();
        Response response = pooulMerchantService.create("10001",cto);
        System.out.println(response.getCode());
    }

    @Test
    public void deleteMerchant() {
        Response response = pooulMerchantService.delete("3497435201252478");
        System.out.println(response.isSuccess());
    }

    @Test
    public void queryBalances() {
        PooulBlancesDTO blancesDTO = pooulBalancesService.queryBalances("2162288807443437");
        System.out.println(blancesDTO.getData().get(0).getBalance());
    }

    @Test
    public void internalTransfers() {
        PooulInternalTransfersTO transfersTO = PooulInternalTransfersTO.builder()
                .payer_merchant_id("2849928048545130")
                .payee_merchant_id("2162288807443437")
                .voucher("T00002")
                .amount(1)
                .transfer_type("结算")
                .op_user_id("100000")
                .build();
        pooulBalancesService.internalTransfers("2849928048545130",transfersTO);
    }

    public static void main(String[] args) {

    }

}
