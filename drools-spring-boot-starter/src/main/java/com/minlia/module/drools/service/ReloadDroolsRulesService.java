package com.minlia.module.drools.service;

import com.minlia.module.drools.entity.Address;
import com.minlia.module.drools.entity.Rule;
import com.minlia.module.drools.repository.RuleRepository;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by neo on 17/7/31.
 */

@Service
public class ReloadDroolsRulesService {

    public static KieContainer kieContainer;

    @Autowired
    private RuleRepository ruleRepository;

    public void reload() {
        KieContainer kieContainer = loadContainerFromString(loadRules());
        this.kieContainer = kieContainer;
    }

    private List<Rule> loadRules() {
        List<Rule> rules = ruleRepository.findAll();
        return rules;
    }

    private KieContainer loadContainerFromString(List<Rule> rules) {
        long startTime = System.currentTimeMillis();
        KieServices ks = KieServices.Factory.get();
        KieRepository kr = ks.getRepository();
        KieFileSystem kfs = ks.newKieFileSystem();

        for (Rule rule : rules) {
            String drl = rule.getContent();
            kfs.write("src/main/resources/rules/" + drl.hashCode() + ".drl", drl);
        }

        KieBuilder kb = ks.newKieBuilder(kfs).buildAll();
        if (kb.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("Build Errors:\n" + kb.getResults().toString());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time to build rules : " + (endTime - startTime) + " ms");
        startTime = System.currentTimeMillis();
        KieContainer kContainer = ks.newKieContainer(kr.getDefaultReleaseId());
        endTime = System.currentTimeMillis();
        System.out.println("Time to load container: " + (endTime - startTime) + " ms");
        return kContainer;
    }

    /**
     * 动态获取KieSession
     *
     * @param rules rule
     * @return KieSession
     */
    public KieSession getKieSession(String rules) {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.write("src/main/resources/rules/rules.drl", rules.getBytes());
        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
            System.out.println(results.getMessages());
//            throw new BusinessException(300003,results.getMessages().toString(),4);
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KieBase kieBase = kieContainer.getKieBase();
        return kieBase.newKieSession();
    }

    /**
     * 动态加载已经部署的rule
     *
     * @param id rule id
     * @param t  对象
     * @return 结果对象
     */
    public Address getRulesWrite(Long id, Address t) {
        Optional<Rule> rule = ruleRepository.findById(id);
        KieSession kieSession = this.getKieSession(rule.get().getContent());
        kieSession.insert(t);
        kieSession.fireAllRules();
        kieSession.dispose();
        return t;
    }

}
