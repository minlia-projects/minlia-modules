package com.minlia.module.lov.aop;

import com.minlia.module.lov.bean.LovValueQRO;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LovAspect {

    @Pointcut("execution(* com.minlia.module.lov.servcie.LovValueService.selectByAll(..)))")
    public void selectPointcut() { }

    @Before(value = "selectPointcut()")
    public void beforeMethod(JoinPoint joinPoint) {
        LovValueQRO qro = (LovValueQRO) joinPoint.getArgs()[0];
        qro.setLocale(LocaleContextHolder.getLocale().toString());

        if (StringUtils.isBlank(qro.getSortsStr())) {
            qro.setSortsStr("sort.ASC");
        }
    }

}
