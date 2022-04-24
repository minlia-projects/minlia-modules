package com.minlia.module.drools.endpoint;

import com.minlia.module.drools.entity.Address;
import com.minlia.module.drools.entity.fact.AddressCheckResult;
import com.minlia.module.drools.service.ReloadDroolsRulesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.kie.api.runtime.KieSession;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(tags = "System Drools Test", description = "规则引擎")
@RequestMapping("api/drools/rules/test")
@Controller
@Profile("dev")
public class TestController {

    @ApiOperation(value = "test")
    @ResponseBody
    @GetMapping("/address")
    public void test(int num) {
        Address address = new Address();
        address.setPostcode(RandomStringUtils.randomNumeric(num));
        KieSession kieSession = ReloadDroolsRulesService.kieContainer.newKieSession();

        AddressCheckResult checkResult = new AddressCheckResult();
        kieSession.insert(address);
        kieSession.insert(checkResult);
        int ruleFiredCount = kieSession.fireAllRules();
        kieSession.destroy();
        System.out.println("触发了" + ruleFiredCount + "条规则");

        if (checkResult.isPostCodeResult()) {
            System.out.println("规则校验通过");
        }
    }

}