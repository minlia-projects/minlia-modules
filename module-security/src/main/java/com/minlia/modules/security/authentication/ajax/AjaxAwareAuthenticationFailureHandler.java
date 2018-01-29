package com.minlia.modules.security.authentication.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minlia.modules.security.body.AuthenticationErrorResponseBody;
import com.minlia.modules.security.code.AuthenticationErrorCode;
import com.minlia.modules.security.exception.AuthMethodNotSupportedException;
import com.minlia.modules.security.exception.JwtAcceptableException;
import com.minlia.modules.security.exception.JwtExpiredTokenException;
import com.minlia.modules.security.exception.JwtInvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AjaxAwareAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper mapper;
    
    @Autowired
    public AjaxAwareAuthenticationFailureHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

		if (e instanceof AuthMethodNotSupportedException)
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.BAD_REQUEST, AuthenticationErrorCode.AUTHENTICATION, "不支持的请求方式"));
		else if (e instanceof AuthenticationServiceException)
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, AuthenticationErrorCode.AUTHENTICATION, "用户名或密码不能为空"));
		else if (e instanceof JwtExpiredTokenException)
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.PROXY_AUTHENTICATION_REQUIRED, AuthenticationErrorCode.JWT_TOKEN_EXPIRED, "Token 已过期"));
		else if (e instanceof JwtInvalidTokenException)
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.PROXY_AUTHENTICATION_REQUIRED, AuthenticationErrorCode.JWT_TOKEN_EXPIRED, "Token 无效"));
		else if (e instanceof JwtAcceptableException)
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.NOT_ACCEPTABLE, AuthenticationErrorCode.AUTHENTICATION,e.getMessage()));
		else if (e instanceof UsernameNotFoundException)
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, AuthenticationErrorCode.AUTHENTICATION, "用户名不存在"));
		else if (e instanceof BadCredentialsException)
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, AuthenticationErrorCode.AUTHENTICATION, "密码错误"));
		else if (e instanceof AccountExpiredException)
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, AuthenticationErrorCode.AUTHENTICATION, "账号已过期"));
		else if (e instanceof DisabledException)
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, AuthenticationErrorCode.AUTHENTICATION, "账号已禁用"));
		else if (e instanceof LockedException)
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, AuthenticationErrorCode.AUTHENTICATION, "账号已锁定"));
		else if (e instanceof CredentialsExpiredException)
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, AuthenticationErrorCode.AUTHENTICATION, "凭证已过期"));
	}

//	@Override
//	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//			AuthenticationException e) throws IOException, ServletException {
////
//		response.setStatus(HttpStatus.UNAUTHORIZED.value());
//		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
////
//		if (e instanceof BadCredentialsException) {
////			mapper.writeValue(response.getWriter(), AuthenticationErrorResponseBody.of("Invalid username or password", AuthenticationErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
////		throw new ApiException(ApiCode.INVALID_CREDENTIAL);
//			ApiPreconditions.checkNotNull(e,ApiCode.INVALID_CREDENTIAL);
//		} else if (e instanceof JwtExpiredTokenException) {
////			mapper.writeValue(response.getWriter(), AuthenticationErrorResponseBody.of("Token has expired", AuthenticationErrorCode.JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED));
////			throw new ApiException(ApiCode.ACCESS_TOKEN_HAS_EXPIRED);
//			ApiPreconditions.checkNotNull(e,ApiCode.ACCESS_TOKEN_HAS_EXPIRED);
//		} else if (e instanceof AuthMethodNotSupportedException) {
////		    mapper.writeValue(response.getWriter(), AuthenticationErrorResponseBody.of(e.getMessage(), AuthenticationErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
////			throw new ApiException(ApiCode.AUTHENTICATION_FAILED);
//			ApiPreconditions.checkNotNull(e,ApiCode.AUTHENTICATION_FAILED);
//		}
////
////		mapper.writeValue(response.getWriter(), AuthenticationErrorResponseBody.of("Authentication failed", AuthenticationErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
////		throw new ApiException(ApiCode.AUTHENTICATION_FAILED);
//		ApiPreconditions.checkNotNull(e,ApiCode.AUTHENTICATION_FAILED);
//	}

}
