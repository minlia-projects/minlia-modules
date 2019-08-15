/*
 *
 *      Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lengleng (wangiegie@gmail.com)
 *
 */

package com.minlia.module.audit.aop;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.entity.AuditLogInfo;
import com.minlia.module.audit.service.AuditLogInfoService;
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
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AuditLogInfoService auditLogInfoService;

    @SneakyThrows
    @Around("auditLog()")
    public Object around(ProceedingJoinPoint joinPoint) {
        AuditLogInfo auditLogInfo = this.builderInfo(joinPoint);
        Long startTime = System.currentTimeMillis();

        Object obj = joinPoint.proceed();

        // 发送异步日志事件
        this.saveAuditLogInfo(auditLogInfo, startTime);
        return obj;
    }

    @SneakyThrows
    @AfterThrowing(pointcut = "auditLog()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        AuditLogInfo auditLogInfo = this.builderInfo(joinPoint);
        Long startTime = System.currentTimeMillis();

        StringJoiner sj = new StringJoiner("--");
        sj.add(ex.toString());
        if (null != ex.getMessage()) {
            sj.add(ex.getMessage());
        }
        if (null != ex.getCause()) {
            sj.add(ex.getCause().getMessage());
        }

        auditLogInfo.setException(sj.toString());
        // 发送异步日志事件
        this.saveAuditLogInfo(auditLogInfo, startTime);
    }

    private void saveAuditLogInfo(AuditLogInfo auditLogInfo, Long startTime) {
        auditLogInfo.setTime(System.currentTimeMillis() - startTime);
        auditLogInfoService.insertSelective(auditLogInfo);
    }

    private AuditLogInfo builderInfo(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();

        AuditLog auditLog = signature.getMethod().getAnnotation(AuditLog.class);
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        AuditLogInfo auditLogInfo = new AuditLogInfo();
        auditLogInfo.setUsername(MinliaSecurityContextHolder.getUsernameOrCellphoneOrEmail());
        auditLogInfo.setCreateBy(MinliaSecurityContextHolder.getCurrentGuid());
        auditLogInfo.setTitle(auditLog.value());
        auditLogInfo.setOperationType(auditLog.type());
        auditLogInfo.setMethod(request.getMethod());
        auditLogInfo.setUserAgent(request.getHeader("user-agent"));
        auditLogInfo.setRequestUri(URLUtil.getPath(request.getRequestURI()));
        auditLogInfo.setRemoteAddr(ServletUtil.getClientIP(request));

        Api api = point.getTarget().getClass().getAnnotation(Api.class);
        if (null != api) {
            auditLogInfo.setTags(api.tags()[0]);
        }

        if (ArrayUtils.isNotEmpty(point.getArgs())) {
            try {
                auditLogInfo.setParams(JSON.toJSONString(point.getArgs()));
            } catch (Exception e) {
                auditLogInfo.setParams(e.getMessage());
            }
        }

        return auditLogInfo;
    }

}
