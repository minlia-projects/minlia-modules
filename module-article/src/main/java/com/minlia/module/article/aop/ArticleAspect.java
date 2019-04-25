//package com.minlia.module.article.aop;
//
//import com.minlia.module.article.ro.ro.ArticleCommentQRO;
//import com.minlia.modules.rbac.context.SecurityContextHolder;
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
//public class ArticleAspect {
//
//    @Pointcut("execution(* com.minlia.module.article.service.ArticleCommentService.*(..)))")
//    public void commentDataDimension() {}
//
//    @Before(value = "commentDataDimension()")
//    public void beforeMethod(JoinPoint joinPoint) {
//        if (joinPoint.getArgs().length > 0 && joinPoint.getArgs()[0] instanceof ArticleCommentQRO) {
//            ArticleCommentQRO ro = (ArticleCommentQRO) joinPoint.getArgs()[0];
//            UserContext userContext = SecurityContextHolder.getUserContext();
//            if (null != userContext && StringUtils.isNotEmpty(userContext.getCurrrole())) {
//                if (!userContext.getCurrrole().equals("Admin")) {
//                    //查询自己
//                    ro.setCreateBy(userContext.getGuid());
//                }
//            }
//        }
//    }
//
//}
