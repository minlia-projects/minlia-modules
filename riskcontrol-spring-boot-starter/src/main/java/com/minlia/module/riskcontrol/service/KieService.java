//package com.minlia.module.riskcontrol.service;
//
//import lombok.extern.slf4j.Slf4j;
//import org.kie.api.io.ResourceType;
//import org.kie.api.runtime.StatelessKieSession;
//import org.kie.internal.builder.KnowledgeBuilder;
//import org.kie.internal.builder.KnowledgeBuilderFactory;
//import org.kie.internal.io.ResourceFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.io.File;
//import java.net.URISyntaxException;
//
///**
// * Created by sunpeak on 2016/8/6.
// */
//@Slf4j
//@Service
//public class KieService {
//
//    private StatelessKieSession kieSession;
//
//    @Autowired
//    private BlackListService blackListService;
//
//    @Autowired
//    private DimensionService dimensionService;
//
//    /**
//     * drools全局服务变量
//     */
//    private void setGlobal() {
//        kieSession.setGlobal("blackListService", blackListService);
//        kieSession.setGlobal("dimensionService", dimensionService);
//    }
//
//    /**
//     * 规则集上线
//     *
//     * @param packageName
//     */
//    public void addPackage(String packageName) {
//        try {
//            File path = new File(this.getClass().getClassLoader().getResource(packageName).toURI().getPath());
//            if (path.isDirectory()) {
//                KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
//                File[] files = path.listFiles();
//                for (File file : files) {
//                    if (file.getName().endsWith(".drl")) {
//                        kbuilder.add(ResourceFactory.newClassPathResource(packageName + "/" + file.getName()), ResourceType.DRL);
//                        if (kbuilder.hasErrors()) {
//                            log.error("Unable to compile drl. " + packageName + file.getName());
//                            return;
//                        } else {
//                            String ruleName = file.getName().replace(".drl", "");
//                            if (kbase.getRule(packageName, ruleName) != null) {
//                                log.info("update rule: " + packageName + "." + ruleName);
//                            } else {
//                                log.info("add rule: " + packageName + "." + ruleName);
//                            }
//                        }
//                    }
//                }
//
//                kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
//                kieSession = kbase.newStatelessKieSession();
//                setGlobal();
//                printRules();
//            }
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 单一规则上线
//     *
//     * @param packageName
//     * @param ruleName
//     */
//    public void addRule(String packageName, String ruleName) {
//        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
//        kbuilder.add(ResourceFactory.newClassPathResource(packageName + "/" + ruleName + ".drl"), ResourceType.DRL);
//        if (kbuilder.hasErrors()) {
//            logger.error("Unable to compile drl. " + packageName + ruleName + ".drl");
//            return;
//        } else {
//            if (kbase.getRule(packageName, ruleName) != null) {
//                logger.info("update rule: " + packageName + "." + ruleName);
//            } else {
//                logger.info("add rule: " + packageName + "." + ruleName);
//            }
//        }
//
//        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
//        kieSession = kbase.newStatelessKieSession();
//        setGlobal();
//        printRules();
//    }
//
//    /**
//     * 单一规则下线
//     *
//     * @param packageName
//     * @param ruleName
//     */
//    public void removeRule(String packageName, String ruleName) {
//        if (kbase.getRule(packageName, ruleName) != null) {
//            kbase.removeRule(packageName, ruleName);
//            logger.info("remove rule: " + packageName + "." + ruleName);
//            kieSession = kbase.newStatelessKieSession();
//            setGlobal();
//            printRules();
//        } else {
//            logger.error("no rule: " + packageName + ruleName);
//        }
//    }
//
//    /**
//     * 规则集下线
//     *
//     * @param packageName
//     */
//    public void removePackage(String packageName) {
//        if (kbase.getKnowledgePackage(packageName) != null) {
//            kbase.removeKnowledgePackage(packageName);
//            logger.info("remove package: " + packageName);
//            kieSession = kbase.newStatelessKieSession();
//            setGlobal();
//            printRules();
//        } else {
//            logger.error("no package: " + packageName);
//        }
//    }
//
//    /**
//     * 打印规则
//     */
//    public void printRules() {
//        log.info("print rule: -----------------------");
//        kbase.getKnowledgePackages().forEach(knowledgePackage ->
//                knowledgePackage.getRules().forEach(rule ->
//                        log.info("print rule: " + knowledgePackage.getName() + "." + rule.getName())));
//        log.info("print rule: -----------------------");
//    }
//
//    @PostConstruct
//    public void init() {
//        addPackage("rules");
//    }
//
//    /**
//     * 规则引擎执行
//     *
//     * @param object
//     */
//    public void execute(Object object) {
//        this.kieSession.execute(object);
//    }
//
//}
