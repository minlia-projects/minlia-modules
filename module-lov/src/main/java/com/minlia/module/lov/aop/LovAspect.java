//package com.minlia.module.lov.aop;
//
//import com.minlia.module.lov.bean.LovValueQro;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
//@Component
//@Aspect
//public class LovAspect {
//
//    @Pointcut("execution(* com.minlia.module.lov.service.LovService.select*ByAll(..)))")
//    public void selectPointcut() {
//    }
//
//    @Before(value = "selectPointcut()")
//    public void beforeMethod(JoinPoint joinPoint) {
//        LovValueQro qro = (LovValueQro) joinPoint.getArgs()[0];
//        if (StringUtils.isBlank(qro.getSortsStr())) {
//            qro.setSortsStr("sort.ASC");
//        }
//    }
//
//}
