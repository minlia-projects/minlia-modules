package com.minlia.module.riskcontrol.controller;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.drools.entity.Address;
import com.minlia.module.drools.entity.fact.AddressCheckResult;
import com.minlia.module.drools.service.ReloadDroolsRulesService;
import com.minlia.module.riskcontrol.event.Event;
import com.minlia.module.riskcontrol.event.LoginEvent;
import com.minlia.module.riskcontrol.service.ConfigService;
import com.minlia.module.riskcontrol.util.EventFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Created by sunpeak on 2016/8/6.
 */
@Api(tags = "System Risk Test", description = "测试")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "riskcontrol")
public class RiskController {

//    @Autowired
//    private KieService kieService;

    @Autowired
    private ConfigService configService;

    @RequestMapping(value = "/req", method = RequestMethod.GET)
    public Response req(String json) {

        Event event = EventFactory.build(json);
        if ("ON".equals(configService.get("SWITCH_RC"))) {
//            kieService.execute(event);
        }
        return Response.success(event);
    }

    @ApiOperation(value = "test")
    @ResponseBody
    @GetMapping("/address")
    public void test(@RequestParam int num){
        KieSession kieSession = ReloadDroolsRulesService.kieContainer.newKieSession();

        Address address = new Address();
        address.setPostcode(RandomStringUtils.randomNumeric(num));
        AddressCheckResult result = new AddressCheckResult();


        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setMobile("123456789");
        loginEvent.setOperateIp("127.0.0.1");

        kieSession.insert(address);
        kieSession.insert(result);

        kieSession.insert(loginEvent);

        int ruleFiredCount = kieSession.fireAllRules();
        kieSession.destroy();
        System.out.println("触发了" + ruleFiredCount + "条规则");

        if(result.isPostCodeResult()){
            System.out.println("规则校验通过");
        }
    }
}
