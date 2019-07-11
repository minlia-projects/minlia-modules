package com.minlia.modules.rebecca.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.cloud.i18n.Lang;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.modules.rebecca.bean.domain.User;
import com.minlia.modules.rebecca.bean.qo.UserQO;
import com.minlia.modules.rebecca.bean.to.PasswordByAccountAndRawPasswordChangeTO;
import com.minlia.modules.rebecca.bean.to.PasswordResetTO;
import com.minlia.modules.rebecca.constant.UserCode;
import com.minlia.modules.rebecca.enumeration.UserUpdateTypeEcnum;
import com.minlia.modules.rebecca.service.UserPasswordService;
import com.minlia.modules.rebecca.service.UserQueryService;
import com.minlia.modules.rebecca.service.UserService;
import com.minlia.modules.security.code.SecurityCode;
import com.minlia.modules.security.exception.AjaxBadCredentialsException;
import com.minlia.modules.security.exception.AjaxLockedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by will on 6/19/17.
 */
@Api(tags = "System Password", description = "密码")
@RestController
@RequestMapping(value = ApiPrefix.API + "user/password")
public class ForgetPasswordEndpoint {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserQueryService userQueryService;

    @Autowired
    private UserPasswordService userPasswordService;

    @AuditLog(value = "change password")
    @ApiOperation(value = "忘记密码", notes = "忘记密码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "forget", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response resetPassword(@Valid @RequestBody PasswordResetTO to) {
        User entity = userPasswordService.forget(to);
        return Response.success(entity);
    }

    @AuditLog(value = "update password by account and raw password")
    @ApiOperation(value = "根据原密码修改", notes = "修改密码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "raw", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response changePassword(@Valid @RequestBody PasswordByAccountAndRawPasswordChangeTO to) {
        User user = null;
        if (StringUtils.isNotBlank(to.getUsername())) {
            user = userQueryService.queryOne(UserQO.builder().username(to.getUsername()).enabled(true).build());
        }
        if (StringUtils.isNotBlank(to.getCellphone())) {
            user = userQueryService.queryOne(UserQO.builder().username(to.getCellphone()).enabled(true).build());
        }
        if (StringUtils.isNotBlank(to.getEmail())) {
            user = userQueryService.queryOne(UserQO.builder().username(to.getEmail()).enabled(true).build());
        }

        ApiAssert.notNull(user, UserCode.Message.NOT_EXISTS);
        ApiAssert.state(!(user.getLocked() && LocalDateTime.now().isBefore(user.getLockTime())), SecurityCode.Exception.AJAX_LOCKED, user.getLockTime());

        if (!encoder.matches(to.getRawPassword(), user.getPassword())) {
            //密码错误 锁定次数+1
            user.setLockLimit(user.getLockLimit() + NumberUtils.INTEGER_ONE);
            //如果超过3次 直接锁定
            if (user.getLockLimit() > 2) {
                user.setLocked(true);
                //1、按错误次数累加时间   2、错误3次锁定一天
                user.setLockTime(LocalDateTime.now().plusMinutes((int) Math.pow(user.getLockLimit() - 3, 3)));
            }
            userService.update(user, UserUpdateTypeEcnum.PASSWORD_ERROR);

            return Response.failure(SecurityCode.Exception.AJAX_BAD_CREDENTIALS,null, user.getLockLimit().toString());
        }
        userPasswordService.change(user, to.getNewPassword());

        return Response.success();
    }

}
