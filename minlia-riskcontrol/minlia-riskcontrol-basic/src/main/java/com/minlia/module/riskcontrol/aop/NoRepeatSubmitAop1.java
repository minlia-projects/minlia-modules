//package com.minlia.module.common.aop;
//
//import com.minlia.cloud.body.Response;
//import com.minlia.module.common.constant.CommonCode;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Objects;
//import java.util.concurrent.TimeUnit;
//
//@Slf4j
//@Aspect
//@Component
//public class NoRepeatSubmitAop1 {
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @Pointcut("@annotation(com.minlia.module.common.annotation.NoRepeatSubmit)")
//    public void noRepeatSubmitAop() {
//    }
//
//    @Around("execution(* com.minlia.*.*Controller.*(..)) || noRepeatSubmitAop()")
//    public Object around(ProceedingJoinPoint pjp) {
//        try {
//            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//            String sessionId = Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getSessionId();
//            assert attributes != null;
//            HttpServletRequest request = attributes.getRequest();
//            String key = sessionId + "-" + request.getServletPath();
//            if (redisTemplate.opsForValue().get(key) == null) {
//                Object o = pjp.proceed();
//                redisTemplate.opsForValue().set(key, 0, 1, TimeUnit.SECONDS);
//                return o;
//            } else {
//                log.info("重复提交");
//                return Response.failure(CommonCode.Message.NO_REPEAT_SUBMIT);
//            }
//        } catch (Throwable e) {
//            log.info("提交次数过多");
//            return Response.failure(CommonCode.Message.NO_REPEAT_SUBMIT);
//        }
//    }
//
//}