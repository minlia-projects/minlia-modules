//package com.minlia.module.drools.config;
//
//import org.kie.api.runtime.rule.ConsequenceExceptionHandler;
//import org.kie.api.runtime.rule.Match;
//import org.kie.api.runtime.rule.RuleRuntime;
//
//import java.io.Externalizable;
//import java.io.IOException;
//import java.io.ObjectInput;
//import java.io.ObjectOutput;
//
//
//public class MyConsequenceExceptionHandler implements ConsequenceExceptionHandler, Externalizable {
//
//    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
//    }
//
//    public void writeExternal(ObjectOutput out) throws IOException {
//    }
//
//    @Override
//    public void handleException(Match match, RuleRuntime ruleRuntime, Exception e) {
//        System.err.println("excpeiton occured when execute rule: " + match.getRule().getName());
//    }
//
//}