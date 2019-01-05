package com.minlia.modules.security.authentication.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minlia.modules.security.body.AuthenticationErrorResponseBody;
import com.minlia.modules.security.code.SecurityCode;
import com.minlia.modules.security.exception.*;
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
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

		//TODO 非自定义异常直接抛出，
		if (e instanceof AuthMethodNotSupportedException) {
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, SecurityCode.Exception.AUTH_METHOD_NOT_SUPPORTED));
		} else if (e instanceof AuthenticationCredentialsNotFoundException) {
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, SecurityCode.Exception.AUTH_CREDENTIALS_NOT_FOUND));
		} else if (e instanceof UsernameNotFoundException) {
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, SecurityCode.Exception.USERNAME_NOT_FOUND));
		} else if (e instanceof BadCredentialsException) {
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, SecurityCode.Exception.BAD_CREDENTIALS));
		} else if (e instanceof AccountExpiredException) {
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, SecurityCode.Exception.ACCOUNT_EXPIRED));
		} else if (e instanceof DisabledException) {
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, SecurityCode.Exception.ACCOUNT_DISABLED));
		} else if (e instanceof LockedException) {
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, SecurityCode.Exception.ACCOUNT_LOCKED));
		} else if (e instanceof CredentialsExpiredException) {
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, SecurityCode.Exception.ACCOUNT_CREDENTIALS_EXPIRED));
		} else if (e instanceof AuthenticationServiceException) {
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, SecurityCode.Exception.AUTH_SERVICE));
		} else if (e instanceof JwtExpiredTokenException) {
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, SecurityCode.Exception.JWT_TOKEN_EXPIRED));
		} else if (e instanceof JwtInvalidTokenException) {
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, SecurityCode.Exception.JWT_TOKEN_INVALID));
		} else if (e instanceof JwtAcceptableException) {
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, SecurityCode.Exception.JWT_TOKEN_NOT_NULL));
		} else if (e instanceof AjaxBadCredentialsException) {
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, SecurityCode.Exception.AJAX_BAD_CREDENTIALS, ((AjaxBadCredentialsException) e).getFailureTimes()));
		} else if (e instanceof AjaxLockedException) {
			mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(HttpStatus.UNAUTHORIZED, SecurityCode.Exception.AJAX_LOCKED, ((AjaxLockedException) e).getLockTime()));
		}
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
