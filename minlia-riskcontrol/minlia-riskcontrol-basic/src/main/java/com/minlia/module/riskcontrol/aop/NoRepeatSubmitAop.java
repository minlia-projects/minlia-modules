package com.minlia.module.riskcontrol.aop;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.SymbolConstants;
import com.minlia.module.riskcontrol.annotation.NoRepeatSubmit;
import com.minlia.module.riskcontrol.constant.RiskcontrolCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 自定义一个切面类，利用aspect实现切入所有方法
 *
 * @author zhouzhaodong
 */
@Slf4j
@Aspect
@Component
public class NoRepeatSubmitAop {

    /**
     * 重复提交判断时间为1s
     */
    private final Cache<String, Integer> CACHE = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(1L, TimeUnit.SECONDS).build();

    /**
     * 调用controller包下的任意类的任意方法时均会调用此方法
     */
    @Around("execution(public * *(..)) && @annotation(com.minlia.module.riskcontrol.annotation.NoRepeatSubmit)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String key = getKey(joinPoint);
        // 如果缓存中有这个url视为重复提交
        if (CACHE.getIfPresent(key) == null) {
            Object obj = joinPoint.proceed();
            CACHE.put(key, 0);
            return obj;
        } else {
            return Response.failure(RiskcontrolCode.Message.FREQUENT_REQUESTS);
        }
    }

    private String getKey(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        NoRepeatSubmit noRepeatSubmit = method.getAnnotation(NoRepeatSubmit.class);

        String sessionId = Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getSessionId();
        String key = sessionId + SymbolConstants.ZHX;
        if (StringUtils.hasLength(noRepeatSubmit.key())) {
            key += key + noRepeatSubmit.key();
        } else {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            assert attributes != null;
            HttpServletRequest request = attributes.getRequest();
            key += request.getServletPath();
        }
        return key;
    }

    //@Around("execution(public * *(..)) && @annotation(com.minlia.module.common.annotation.NoRepeatSubmit)")
    //public Object interceptor(ProceedingJoinPoint pjp) {
    //    MethodSignature signature = (MethodSignature) pjp.getSignature();
    //    Method method = signature.getMethod();
    //    NoRepeatSubmit noRepeatSubmit = method.getAnnotation(NoRepeatSubmit.class);
    //
    //    String key = getKey(noRepeatSubmit.key(), pjp.getArgs());
    //    if (!StringUtils.isEmpty(key)) {
    //        if (CACHE.getIfPresent(key) != null) {
    //            throw new RuntimeException("请勿重复请求");
    //        }
    //        // 如果是第一次请求,就将 key 当前对象压入缓存中
    //        CACHE.put(key, 0);
    //    }
    //    try {
    //        return pjp.proceed();
    //    } catch (Throwable throwable) {
    //        throw new RuntimeException("服务器异常");
    //    } finally {
    //        // TODO 为了演示效果,这里就不调用 CACHES.invalidate(key); 代码了
    //    }
    //}
    //
    ///**
    // * key 的生成策略,如果想灵活可以写成接口与实现类的方式（TODO 后续讲解）
    // *
    // * @param keyExpress 表达式
    // * @param args       参数
    // * @return 生成的key
    // */
    //private String getKey(String keyExpress, Object[] args) {
    //    for (int i = 0; i < args.length; i++) {
    //        keyExpress = keyExpress.replace("arg[" + i + "]", args[i].toString());
    //    }
    //    return keyExpress;
    //}

}