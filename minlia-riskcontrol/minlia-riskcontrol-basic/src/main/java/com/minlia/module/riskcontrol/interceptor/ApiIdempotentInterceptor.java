package com.minlia.module.riskcontrol.interceptor;

import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.riskcontrol.annotation.ApiIdempotent;
import com.minlia.module.riskcontrol.constant.RiskcontrolCode;
import com.minlia.module.riskcontrol.service.ApiIdempotentTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 拦截器
 *
 * @author garen
 */
@Component
@RequiredArgsConstructor
public class ApiIdempotentInterceptor implements HandlerInterceptor {

    private final ApiIdempotentTokenService apiIdempotentTokenService;

    /**
     * 预处理 * * @param request * @param response * @param handler * @return * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //被AutoIdempotent标记的扫描
        ApiIdempotent methodAnnotation = method.getAnnotation(ApiIdempotent.class);
        if (methodAnnotation != null) {
            try {
                // 幂等性校验, 校验通过则放行, 校验失败则抛出异常, 并通过统一异常处理返回友好提示
                return apiIdempotentTokenService.check(request, response);
            } catch (Exception ex) {
                ApiAssert.state(false, RiskcontrolCode.Message.NO_REPEAT_SUBMIT);
            }
        }
        //必须返回true,否则会被拦截一切请求
        return true;
    }

}