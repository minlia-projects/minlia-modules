package com.minlia.modules.rbac.v1.backend.password;

import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.captcha.v1.enumeration.SecureCodeSceneEnum;
import com.minlia.module.captcha.v1.service.SecureCodeService;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import com.minlia.modules.rbac.domain.User;
import com.minlia.modules.rbac.repository.RoleRepository;
import com.minlia.modules.rbac.repository.UserRepository;
import com.minlia.modules.rbac.service.UserReadOnlyService;
import com.minlia.modules.rbac.v1.backend.changempassword.body.ChangePasswordByRawPasswordRequestBody;
import com.minlia.modules.rbac.v1.backend.changempassword.body.ChangePasswordBySecurityCodeRequestBody;
import com.minlia.modules.rbac.v1.openapi.resetpassword.ResetPasswordRequestBody;
import com.minlia.modules.security.code.SecurityApiCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserPasswordServiceImpl implements UserPasswordService {

  @Autowired
  UserRepository userRepository;


  @Autowired
  UserReadOnlyService userReadOnlyService;


  @Autowired
  RoleRepository roleRepository;

  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  SecureCodeService secureCodeService;


  /**
   * 重置密码
   */
  @Override
  public User resetPassword(ResetPasswordRequestBody body) {
    ApiPreconditions.checkNotNull(body.getUsername(), SecurityApiCode.INVALID_USER);

    //验证验证码是否正确
    Boolean validity = secureCodeService
        .validity(body.getUsername(), body.getCode(), SecureCodeSceneEnum.RESET_PASSWORD);
    ApiPreconditions.not(validity, SecurityApiCode.INVALID_SECURE_CODE);

    //查找当前用户
    User userFound = userReadOnlyService.findOneByUsernameOrEmailOrCellphone(body.getUsername());

    //密码
    userFound.setPassword(bCryptPasswordEncoder.encode(body.getNewPassword()));

    //用户可以登录相关开始
    userFound.setEnabled(Boolean.TRUE);
    userFound.setExpired(Boolean.FALSE);
    userFound.setCredentialsExpired(Boolean.FALSE);
    userFound.setLocked(Boolean.FALSE);
//        用户可以登录相关结束

    User userUpdated = userRepository.save(userFound);

    //清除验证码
    secureCodeService.clean(body.getUsername(), body.getCode(), SecureCodeSceneEnum.RESET_PASSWORD);

    //TODO AuditPasswordReset Log
    return userUpdated;
  }


  @Override
  public User changePassword(ChangePasswordBySecurityCodeRequestBody body) {

    String username = SecurityContextHolder.getCurrentUserLogin();

    //验证验证码是否正确
    Boolean validity = secureCodeService
        .validity(username, body.getCode(), SecureCodeSceneEnum.CHANGE_PASSWORD);
    ApiPreconditions.not(validity, SecurityApiCode.INVALID_SECURE_CODE);

    //查找当前用户
    User userFound = userReadOnlyService.findOneByUsernameOrEmailOrCellphone(username);

    //密码
    userFound.setPassword(bCryptPasswordEncoder.encode(body.getNewPassword()));

    //用户可以登录相关开始
    userFound.setEnabled(Boolean.TRUE);
    userFound.setExpired(Boolean.FALSE);
    userFound.setCredentialsExpired(Boolean.FALSE);
    userFound.setLocked(Boolean.FALSE);
//        用户可以登录相关结束

    User userUpdated = userRepository.save(userFound);

    //清除验证码
    secureCodeService.clean(username, body.getCode(), SecureCodeSceneEnum.CHANGE_PASSWORD);

    //TODO AuditPasswordChanged Log
    return userUpdated;
  }


  @Override
  public User changePassword(ChangePasswordByRawPasswordRequestBody body) {
    String username = SecurityContextHolder.getCurrentUserLogin();
    User userFound = userReadOnlyService.findOneByUsernameOrEmailOrCellphone(username);
    String rawPassword = userFound.getPassword();
    String bodyRawPasswordEncoded = bCryptPasswordEncoder.encode(body.getRawPassword());
    Boolean equals = rawPassword.equals(bodyRawPasswordEncoded);

    //原密码输入是否正确, 不正确抛出异常
    ApiPreconditions.not(equals, ApiCode.INVALID_RAW_PASSWORD,"原密码错误");

    //设置新密码
    userFound.setPassword(bodyRawPasswordEncoded);

    //用户可以登录相关开始
    userFound.setEnabled(Boolean.TRUE);
    userFound.setExpired(Boolean.FALSE);
    userFound.setCredentialsExpired(Boolean.FALSE);
    userFound.setLocked(Boolean.FALSE);
//        用户可以登录相关结束

    User userUpdated = userRepository.save(userFound);
    //TODO AuditPasswordChanged Log
    return userUpdated;
  }

}
