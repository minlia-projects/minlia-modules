package com.minlia.modules.rebecca.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.OperationTypeEnum;
import com.minlia.modules.rebecca.bean.domain.User;
import com.minlia.modules.rebecca.bean.qo.UserQO;
import com.minlia.modules.rebecca.service.LoginService;
import com.minlia.modules.rebecca.service.UserQueryService;
import com.minlia.modules.security.authentication.jwt.extractor.TokenExtractor;
import com.minlia.modules.security.authentication.jwt.verifier.TokenVerifier;
import com.minlia.modules.security.autoconfiguration.JwtProperty;
import com.minlia.modules.security.autoconfiguration.WebSecurityConfig;
import com.minlia.modules.security.exception.InvalidJwtTokenException;
import com.minlia.modules.security.model.UserContext;
import com.minlia.modules.security.model.token.JwtTokenFactory;
import com.minlia.modules.security.model.token.RawAccessJwtToken;
import com.minlia.modules.security.model.token.RefreshToken;
import com.minlia.modules.security.model.token.TokenCacheUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Autowired
    private UserQueryService userQueryService;

    @AuditLog(value = "logout", type = OperationTypeEnum.MODIFY)
    @ApiOperation(value = "注销")
    @PostMapping(value = ApiPrefix.V1 + "auth/logout")
    public @ResponseBody
    Response logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String guid = com.minlia.modules.rebecca.context.SecurityContextHolder.getCurrentGuid();
        TokenCacheUtils.kill(guid);
//        response.getWriter().flush();
//        response.getWriter().close();
//        WebUtils.setSessionAttribute(request, "SESSION", null);
        return Response.success();
    }

    @AuditLog(value = "refresh authentication token", type = OperationTypeEnum.INFO)
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

//        String subject = refreshToken.getSubject();
        String guid = refreshToken.getClaims().getBody().get("guid", String.class);
        User user = userQueryService.queryOne(UserQO.builder().guid(guid).build());
        if (null == user) {
            throw new UsernameNotFoundException("User not found: " + guid);
        }

        //如果当前角色为空获取默认角色
        String currrole = refreshToken.getClaims().getBody().get("currrole", String.class);
        UserContext userContext = loginService.getUserContext(user, currrole);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
        return Response.success(tokenFactory.createAccessJwtToken(userContext, UUID.randomUUID().toString()));
    }

}
