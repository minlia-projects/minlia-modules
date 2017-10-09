package com.minlia.module.tenant.batis.web;

import com.minlia.cloud.body.impl.FailureResponseBody;
import com.minlia.cloud.marshall.JsonHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import com.minlia.module.tenant.resolver.ThreadLocalBatisTenantIdentifierResolver;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
@Slf4j
public class AppidWebInterceptor extends HandlerInterceptorAdapter {

  private static final String HEADER_PARAMETER = "X-REQUEST-APPID";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String value = request.getHeader(HEADER_PARAMETER);
    boolean requested = false;
    if (value != null && !value.isEmpty()) {
      log.info(HEADER_PARAMETER+" in header with value {}",value);
      ThreadLocalBatisTenantIdentifierResolver.setCurrentTenantIdentifier(value);
      requested = true;
    } else {
      response.setStatus(HttpServletResponse.SC_OK);
      FailureResponseBody body=FailureResponseBody.builder().message("No "+HEADER_PARAMETER+" supplied in header").build();
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.getWriter().write(JsonHelper.serialize(body));
      response.getWriter().flush();
    }
    return requested;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    ThreadLocalBatisTenantIdentifierResolver.remove();
  }
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    ThreadLocalBatisTenantIdentifierResolver.remove();
  }

  public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    ThreadLocalBatisTenantIdentifierResolver.remove();
  }
}
