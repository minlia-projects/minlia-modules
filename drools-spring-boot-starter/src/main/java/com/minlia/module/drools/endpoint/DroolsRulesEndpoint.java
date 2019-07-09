package com.minlia.module.drools.endpoint;

import com.minlia.module.drools.service.ReloadDroolsRulesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(tags = "System Drools Rules", description = "引擎规则")
@RequestMapping("api/drools/rules")
@Controller
public class DroolsRulesEndpoint {

    @Autowired
    private ReloadDroolsRulesService reloadDroolsRulesService;

    @ApiOperation(value = "重新加载")
    @ResponseBody
    @PostMapping("/reload")
    public String reload() {
        reloadDroolsRulesService.reload();
        return "ok";
    }

//    @ApiOperation(value = "验证规则是否合法",notes = verify)
//    @RequestMapping(value = "/verify", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    public Response ruleVerify(@RequestParam(value = "rule") String rule,
//                                   @RequestParam(value = "data") String json) {
//        KieSession kieSession = reloadDroolsRulesService.getKieSession(rule);
//        Gson gson = new Gson();
//        Person person = gson.fromJson(json, Person.class);
//        kieSession.insert(person);
//        int rules=kieSession.fireAllRules();
//        System.out.println(rules);
//        kieSession.dispose();
//        return new JsonResponse(person);
//    }



    private final static String verify = "package plausibcheck.adress\n" +
            "\n" +
            "import com.minlia.module.drools.entity.Address;\n" +
            "import com.minlia.module.drools.entity.fact.AddressCheckResult;\n" +
            "\n" +
            "rule \"Postcode 6 numbers\"\n" +
            "\n" +
            "    when\n" +
            "        address : Address(postcode != null, postcode matches \"([0-9]{6})\")\n" +
            "        checkResult : AddressCheckResult();\n" +
            "    then\n" +
            "        checkResult.setPostCodeResult(true);\n" +
            "\t\tSystem.out.println(\"规则6中打印日志：校验通过!\");\n" +
            "end";

}