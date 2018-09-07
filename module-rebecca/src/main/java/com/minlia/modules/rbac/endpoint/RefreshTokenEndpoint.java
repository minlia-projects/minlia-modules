package com.minlia.modules.rbac.endpoint;

import com.google.common.collect.Lists;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.modules.rbac.backend.permission.service.PermissionService;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.backend.user.service.UserQueryService;
import com.minlia.modules.security.authentication.jwt.extractor.TokenExtractor;
import com.minlia.modules.security.authentication.jwt.verifier.TokenVerifier;
import com.minlia.modules.security.autoconfiguration.JwtProperty;
import com.minlia.modules.security.autoconfiguration.WebSecurityConfig;
import com.minlia.modules.security.constant.SecurityConstant;
import com.minlia.modules.security.exception.InvalidJwtTokenException;
import com.minlia.modules.security.model.UserContext;
import com.minlia.modules.security.model.token.JwtTokenFactory;
import com.minlia.modules.security.model.token.RawAccessJwtToken;
import com.minlia.modules.security.model.token.RefreshToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "System Security", description = "系统安全")
public class RefreshTokenEndpoint {

    @Autowired
    private JwtProperty jwtProperty;

    @Autowired
    private TokenVerifier tokenVerifier;

    @Autowired
    private JwtTokenFactory tokenFactory;

    @Autowired
    @Qualifier("jwtHeaderTokenExtractor")
    private TokenExtractor tokenExtractor;

    @Autowired
    private UserQueryService userQueryService;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "刷新令牌", notes = "刷新令牌, 正常情况下TOKEN值在请求时以Header参数 X-Auth-Token: Bearer xxxxxx传入", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/api/v1/auth/refreshToken", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})//,headers = "X-Authorization"
    public @ResponseBody StatefulBody refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));

        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtProperty.getTokenSigningKey()).orElseThrow(() -> new InvalidJwtTokenException());

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtTokenException();
        }

        String subject = refreshToken.getSubject();
        User user = userQueryService.queryByGuid(subject);
        if (null == user) {
            throw new UsernameNotFoundException("User not found: " + subject);
        }

        //如果当前角色为空获取默认角色
        String currrole = user.getDefaultRole();
        if (StringUtils.isBlank(currrole)) {
            currrole = SecurityConstant.ROLE_USER_CODE;
        }
        List<GrantedAuthority> authorities= permissionService.getGrantedAuthority(Lists.newArrayList(currrole));
        UserContext userContext = UserContext.builder().username(user.getUsername()).guid(user.getGuid()).currrole(currrole).authorities(authorities).build();

        log.info("*****************************************************2");
        log.info("*****************************************************2");
        log.info("*****************************************************2");
        log.info("*****************************************************2" + userContext.toString());

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userContext, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(token);
        return SuccessResponseBody.builder().payload(tokenFactory.createAccessJwtToken(userContext)).build();
    }

}
