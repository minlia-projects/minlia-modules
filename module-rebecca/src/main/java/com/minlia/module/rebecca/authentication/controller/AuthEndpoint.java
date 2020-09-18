package com.minlia.module.rebecca.authentication.controller;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.AuditOperationTypeEnum;
import com.minlia.module.rebecca.authentication.bean.UserLogonResponseBody;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import com.minlia.module.rebecca.authentication.service.LoginService;
import com.minlia.modules.security.authentication.jwt.extractor.TokenExtractor;
import com.minlia.modules.security.authentication.jwt.verifier.TokenVerifier;
import com.minlia.modules.security.autoconfiguration.JwtProperty;
import com.minlia.modules.security.autoconfiguration.WebSecurityConfig;
import com.minlia.modules.security.exception.InvalidJwtTokenException;
import com.minlia.modules.security.model.UserContext;
import com.minlia.modules.security.model.token.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@Api(tags = "System Security", description = "系统安全")
public class AuthEndpoint {

    @Autowired
    private JwtProperty jwtProperty;

    @Autowired
    private LoginService loginService;

    @Autowired
    private TokenVerifier tokenVerifier;

    @Autowired
    private JwtTokenFactory tokenFactory;

    @Autowired
    @Qualifier("jwtHeaderTokenExtractor")
    private TokenExtractor tokenExtractor;

    @AuditLog(value = "logout", type = AuditOperationTypeEnum.UPDATE)
    @ApiOperation(value = "注销")
    @PostMapping(value = ApiPrefix.V1 + "auth/logout")
    public @ResponseBody
    Response logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long uid = SecurityContextHolder.getUid();
        TokenCacheUtils.kill(uid);
        response.getWriter().flush();
        response.getWriter().close();
        WebUtils.setSessionAttribute(request, "SESSION", null);
        return Response.success();
    }

    @AuditLog(value = "refresh authentication token", type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "刷新令牌", notes = "刷新令牌, 正常情况下TOKEN值在请求时以Header参数 X-Auth-Token: Bearer xxxxxx传入", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = ApiPrefix.V1 + "auth/refreshToken", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Response refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //,headers = "X-Authorization"
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));
        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
//        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtProperty.getTokenSigningKey()).orElseThrow(() -> new InvalidJwtTokenException());
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtProperty.getTokenSigningKey()).get();

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtTokenException();
        }

        Long uid = refreshToken.getClaims().getBody().get("uid", Long.class);
        UserContext userContext = UserContext.builder().uid(uid).build();
        String id = UUID.randomUUID().toString();
        JwtToken accessToken = tokenFactory.createAccessJwtToken(userContext, id);
        JwtToken refreshToken1 = tokenFactory.createRefreshToken(userContext, id);

        UserLogonResponseBody responseBody = UserLogonResponseBody.builder()
                .token(accessToken.getToken())
                .loginEffectiveDate(((AccessJwtToken) accessToken).getClaims().getExpiration().getTime())
                .refreshToken(refreshToken1.getToken())
                .build();

        //重置缓存时间
        TokenCacheUtils.expire(userContext.getUid(), jwtProperty.getTokenExpirationTime());
        return Response.success(responseBody);
    }

}
