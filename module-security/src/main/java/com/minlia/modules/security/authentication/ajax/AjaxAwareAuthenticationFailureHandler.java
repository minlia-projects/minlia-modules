package com.minlia.modules.security.authentication.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.exception.ApiException;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.modules.security.body.AuthenticationErrorResponseBody;
import com.minlia.modules.security.code.AuthenticationErrorCode;
import com.minlia.modules.security.exception.AuthMethodNotSupportedException;
import com.minlia.modules.security.exception.JwtExpiredTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
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
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException {
//
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//
		if (e instanceof BadCredentialsException) {
			mapper.writeValue(response.getWriter(), AuthenticationErrorResponseBody.of("Invalid username or password", AuthenticationErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
//		throw new ApiException(ApiCode.INVALID_CREDENTIAL);
//			ApiPreconditions.checkNotNull(e,ApiCode.INVALID_CREDENTIAL);
		} else if (e instanceof JwtExpiredTokenException) {
			mapper.writeValue(response.getWriter(), AuthenticationErrorResponseBody.of("Token has expired", AuthenticationErrorCode.JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED));
//			throw new ApiException(ApiCode.ACCESS_TOKEN_HAS_EXPIRED);
//			ApiPreconditions.checkNotNull(e,ApiCode.ACCESS_TOKEN_HAS_EXPIRED);
		} else if (e instanceof AuthMethodNotSupportedException) {
		    mapper.writeValue(response.getWriter(), AuthenticationErrorResponseBody.of(e.getMessage(), AuthenticationErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
//			throw new ApiException(ApiCode.AUTHENTICATION_FAILED);
//			ApiPreconditions.checkNotNull(e,ApiCode.AUTHENTICATION_FAILED);
		}
//
		mapper.writeValue(response.getWriter(), AuthenticationErrorResponseBody.of("Authentication failed", AuthenticationErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
//		throw new ApiException(ApiCode.AUTHENTICATION_FAILED);
//		ApiPreconditions.checkNotNull(e,ApiCode.AUTHENTICATION_FAILED);


	}
}
