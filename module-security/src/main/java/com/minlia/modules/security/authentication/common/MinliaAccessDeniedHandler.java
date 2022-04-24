package com.minlia.modules.security.authentication.common;

import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.security.code.SecurityCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AccessDeniedHandler这处理器当认证成功的用户访问受保护的资源，但是权限不够，则会进入这个处理器进行处理
 *
 * @author garen
 */
@Component
public class MinliaAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ApiAssert.state(false, SecurityCode.Exception.AUTH_CREDENTIALS_NOT_FOUND);
    }

}