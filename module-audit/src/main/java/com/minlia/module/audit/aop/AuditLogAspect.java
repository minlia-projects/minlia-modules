package com.minlia.module.audit.aop;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.minlia.cloud.constant.SymbolConstants;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.entity.AuditLogEntity;
import com.minlia.module.audit.event.AuditSaveEvent;
import com.minlia.modules.security.context.MinliaSecurityContextHolder;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * 操作日志使用spring event异步入库
 *
 * @author L.cm
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class AuditLogAspect {

    @Pointcut("@annotation(com.minlia.module.audit.annotation.AuditLog)")
    public void auditLog() {
    }

    @SneakyThrows
    @Around("auditLog()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Long startTime = System.currentTimeMillis();

        Object obj = joinPoint.proceed();

        // 发送异步日志事件
        AuditLogEntity auditLogEntity = this.builderInfo(joinPoint, startTime);
        auditLogEntity.setSuccessFlag(true);
        AuditSaveEvent.publish(auditLogEntity);
        return obj;
    }

    @SneakyThrows
    @AfterThrowing(pointcut = "auditLog()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        Long startTime = System.currentTimeMillis();

        StringJoiner sj = new StringJoiner("--");
        sj.add(ex.toString());
        if (null != ex.getMessage()) {
            sj.add(ex.getMessage());
        }
        if (null != ex.getCause()) {
            sj.add(ex.getCause().getMessage());
        }

        // 发送异步日志事件
        AuditLogEntity auditLogEntity = this.builderInfo(joinPoint, startTime);
        auditLogEntity.setSuccessFlag(false);
        auditLogEntity.setException(sj.toString());
        AuditSaveEvent.publish(auditLogEntity);
    }

    private AuditLogEntity builderInfo(JoinPoint point, long startTime) {
        MethodSignature signature = (MethodSignature) point.getSignature();

        AuditLog auditLog = signature.getMethod().getAnnotation(AuditLog.class);
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        AuditLogEntity auditLogInfo = new AuditLogEntity();
        auditLogInfo.setUsername(MinliaSecurityContextHolder.getName());
        auditLogInfo.setCreateBy(MinliaSecurityContextHolder.getUid());
        auditLogInfo.setOperationType(auditLog.type());
        auditLogInfo.setMethod(request.getMethod());
        auditLogInfo.setUserAgent(request.getHeader("user-agent"));
        auditLogInfo.setRequestUri(URLUtil.getPath(request.getRequestURI()));
        auditLogInfo.setRemoteAddr(ServletUtil.getClientIP(request));
        Api api = point.getTarget().getClass().getAnnotation(Api.class);
        if (null != api) {
            auditLogInfo.setTags(api.tags()[0]);
        }
        auditLogInfo.setTitle(SymbolConstants.EMPTY.equals(auditLog.value()) ? point.getSignature().getName() : auditLog.value());

        if (ArrayUtils.isNotEmpty(point.getArgs())) {
            try {
                auditLogInfo.setParams(point.getArgs().toString());
            } catch (Exception e) {
                auditLogInfo.setParams(e.getMessage());
            }
        }
        auditLogInfo.setTime(System.currentTimeMillis() - startTime);
        return auditLogInfo;
    }

//    @Pointcut("execution(* com.minlia.module.audit.service.AuditLogInfoService.query*(..)))")
//    public void queryPointcut() {
//    }
//
//
//    @SneakyThrows
//    @Before("queryPointcut()")
//        public void queryBefore(ProceedingJoinPoint joinPoint) {
//        if (joinPoint.getArgs().length > 0 && joinPoint.getArgs()[0] instanceof AuditLogInfoQRO) {
//            AuditLogInfoQRO cro = (AuditLogInfoQRO) joinPoint.getArgs()[0];
//
//            UserContext userContext = MinliaSecurityContextHolder.getUserContext();
//        }
//    }

}