package com.minlia.module.rebecca.authentication;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.minlia.module.audit.entity.AuditLogEntity;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.audit.event.AuditSaveEvent;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author garen
 * @date 2018/5/27
 */
@Aspect
@Component
public class LoginAspect {

    @Pointcut("execution (* com.minlia.module.rebecca.authentication.RbacAuthenticationService.authentication(..))")
    public void login() {
    }

    @SneakyThrows
    @Around("login()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Long startTime = System.currentTimeMillis();

        Object obj = joinPoint.proceed();

        // 发送异步日志事件
        AuditLogEntity entity = builderInfo(joinPoint, startTime);
        entity.setSuccessFlag(true);
        AuditSaveEvent.publish(entity);
        return obj;
    }

    private AuditLogEntity builderInfo(JoinPoint joinPoint, long startTime) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        AuditLogEntity auditLogInfo = new AuditLogEntity();
        auditLogInfo.setUsername(((UsernamePasswordAuthenticationToken) joinPoint.getArgs()[0]).getName());
        auditLogInfo.setOperationType(AuditOperationTypeEnum.LOGIN);
        auditLogInfo.setMethod(request.getMethod());
        auditLogInfo.setTags("System Security");
        auditLogInfo.setTitle("System Security Login");
        auditLogInfo.setUserAgent(request.getHeader("user-agent"));
        auditLogInfo.setRequestUri(URLUtil.getPath(request.getRequestURI()));
        auditLogInfo.setRemoteAddr(ServletUtil.getClientIP(request));
        auditLogInfo.setTime(System.currentTimeMillis() - startTime);
        return auditLogInfo;
    }

}