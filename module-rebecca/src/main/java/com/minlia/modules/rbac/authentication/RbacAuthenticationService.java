package com.minlia.modules.rbac.authentication;

import com.google.common.collect.Lists;
import com.minlia.modules.http.NetworkUtil;
import com.minlia.modules.rbac.backend.permission.service.PermissionService;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.backend.user.mapper.UserMapper;
import com.minlia.modules.rbac.backend.user.service.UserService;
import com.minlia.modules.rbac.event.LoginSuccessEvent;
import com.minlia.modules.security.authentication.credential.LoginCredentials;
import com.minlia.modules.security.authentication.service.AuthenticationService;
import com.minlia.modules.security.constant.SecurityConstant;
import com.minlia.modules.security.exception.AjaxBadCredentialsException;
import com.minlia.modules.security.exception.AjaxLockedException;
import com.minlia.modules.security.model.UserContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * Created by will on 8/14/17.
 * 框架提供的抽象认证实现
 */
@Component
@Primary
public class RbacAuthenticationService implements AuthenticationService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private PermissionService permissionService;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Authentication authentication(Authentication authentication) {
        Assert.notNull(authentication, "No authentication data provided");
//        String username = (String) authentication.getPrincipal();
        LoginCredentials loginCredentials = (LoginCredentials) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        String currrole = loginCredentials.getCurrrole();

        User user = userMapper.queryByUsernameOrCellphoneOrEmail(loginCredentials.getUsername());
        if (null == user) {
            throw new UsernameNotFoundException("User not exists:");
        } else if (null != user.getExpireDate() && user.getExpireDate().before(new Date())) {
            throw new AccountExpiredException("账号已过期");
        } else if (!user.getEnabled()) {
            throw new DisabledException("账号已禁用");
        } else if (user.getCredentialsExpired()) {
            throw new CredentialsExpiredException("凭证已过期");
        } else if (user.getLocked() && new Date().before(user.getLockTime())) {
            throw new AjaxLockedException("账号已锁定", ChronoUnit.SECONDS.between(new Date().toInstant(),user.getLockTime().toInstant()));
        } else if (!encoder.matches(password,user.getPassword())) {
            //密码错误 锁定次数+1
            user.setLockLimit(user.getLockLimit()+ NumberUtils.INTEGER_ONE);
            //如果超过3次 直接锁定
            if (user.getLockLimit() > 2) {
                user.setLocked(true);
                //1、按错误次数累加时间   2、错误3次锁定一天
                user.setLockTime(DateUtils.addMinutes(new Date(), (int) Math.pow(user.getLockLimit()-3,3)));
            }
            userService.update(user);
            throw new AjaxBadCredentialsException("Password error",user.getLockLimit());
        } else {
            //获取请求IP地址
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            String ipAddress = NetworkUtil.getIpAddress(request);

            //更新用户信息
            user.setLocked(Boolean.FALSE);
            user.setLockLimit(NumberUtils.INTEGER_ZERO);
            user.setLastLoginTime(new Date());
            user.setLastLoginIp(ipAddress);
            userMapper.update(user);

            //如果当前角色为空获取默认角色
            if (StringUtils.isBlank(currrole)) {
                if (StringUtils.isNotBlank(user.getDefaultRole())) {
                    currrole = user.getDefaultRole();
                } else {
                    currrole = SecurityConstant.ROLE_GUEST_CODE;
                }
            }

            //获取登录角色权限点
            List<GrantedAuthority> authorities= permissionService.getGrantedAuthority(Lists.newArrayList(currrole));
            UserContext userContext = UserContext.builder().username(user.getUsername()).guid(user.getGuid()).currrole(currrole).authorities(authorities).build();

            //登录成功事件
            LoginSuccessEvent.onSuccess(user);
            return new UsernamePasswordAuthenticationToken(userContext, null, authorities);
        }
    }

}
