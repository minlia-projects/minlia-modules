package com.minlia.module.riskcontrol.controller;

import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.drools.entity.Address;
import com.minlia.module.drools.entity.fact.AddressCheckResult;
import com.minlia.module.drools.service.ReloadDroolsRulesService;
import com.minlia.module.riskcontrol.event.LoginEvent;
import com.minlia.module.riskcontrol.service.BlackListService;
import com.minlia.module.riskcontrol.service.DimensionService;
import com.minlia.module.riskcontrol.service.RiskConfigService;
import com.minlia.module.riskcontrol.service.RiskRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * Created by sunpeak on 2016/8/6.
 */
@Api(tags = "System Risk Test", description = "测试")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "riskcontrol")
@Profile("dev")
public class RiskController {

//    @Autowired
//    private KieService kieService;

    @Autowired
    private RiskConfigService riskConfigService;

    @Autowired
    private BlackListService blackListService;

    @Autowired
    private DimensionService dimensionService;

    @Autowired
    private RiskRecordService riskRecordService;

//    @RequestMapping(value = "/req", method = RequestMethod.GET)
//    public Response req(String json) {
//
////        Event event = EventFactory.build(json);
////        if ("ON".equals(riskConfigService.get("SWITCH_RC"))) {
////            kieService.execute(event);
////        }
//        return Response.success(event);
//    }

    @ApiOperation(value = "test")
    @ResponseBody
    @GetMapping("/address")
    public void test(@RequestParam int num){
        KieSession kieSession = ReloadDroolsRulesService.kieContainer.newKieSession();

//        kieSession.setGlobal("blackListService", blackListService);
//        kieSession.setGlobal("riskEventListService", riskRecordService);

        Address address = new Address();
        address.setPostcode(RandomStringUtils.randomNumeric(num));
        AddressCheckResult result = new AddressCheckResult();

        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setOperateTime(LocalDateTime.now());
        loginEvent.setUsername("123456789");
        loginEvent.setIp("127.0.0.1");

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

    @ApiOperation(value = "test1")
    @ResponseBody
    @GetMapping("/login")
    public void test1(@RequestParam String mobile){
        StatelessKieSession kieSession = ReloadDroolsRulesService.kieContainer.newStatelessKieSession();

        kieSession.setGlobal("blackListService", blackListService);
        kieSession.setGlobal("dimensionService", dimensionService);
        kieSession.setGlobal("riskRecordService", riskRecordService);

        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setScene("login_ip");
        loginEvent.setIp("127.0.0.1");
        loginEvent.setUsername(mobile);
        loginEvent.setOperateTime(LocalDateTime.now());

        kieSession.execute(loginEvent);
        System.out.println(loginEvent.getIp());
    }

}
