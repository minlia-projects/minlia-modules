//package com.minlia.module.article.aop;
//
//import com.minlia.module.article.ro.ArticleCollectionQRO;
//import com.minlia.modules.rebecca.context.SecurityContextHolder;
//import com.minlia.modules.security.model.UserContext;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
///**
// * Created by garen on 2018/8/18.
// */
//@Component
//@Aspect
//public class ArticleCollectionAop {
//
//    @Pointcut("execution(* com.minlia.module.article.service.ArticleCollectionService.query*(..)))")
//    public void dataDimension() {}
//
//    @Before(value = "dataDimension()")
//    public void beforeMethod(JoinPoint joinPoint) {
//        if (joinPoint.getArgs().length > 0 && joinPoint.getArgs()[0] instanceof ArticleCollectionQRO) {
//            ArticleCollectionQRO qro = (ArticleCollectionQRO) joinPoint.getArgs()[0];
//            UserContext userContext = SecurityContextHolder.getUserContext();
//            if (null != userContext && StringUtils.isNotEmpty(userContext.getCurrrole())) {
//                if (!userContext.getCurrrole().equals("Admin")) {
//                    //查询自己
//                    qro.setCollector(userContext.getGuid());
//                }
//            }
//        }
//    }
//
//}
