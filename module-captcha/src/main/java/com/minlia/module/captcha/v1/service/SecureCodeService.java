package com.minlia.module.captcha.v1.service;


import com.minlia.module.captcha.v1.body.SecureCodeSendRequestBody;
import com.minlia.module.captcha.v1.domain.SecureCode;
import com.minlia.module.captcha.v1.enumeration.SecureCodeSceneEnum;
import org.springframework.transaction.annotation.Transactional;

/**
 * 验证码服务
 * <p>
 * 持久化一个新的secure Code
 * 清除所有已过期的secure code
 * validity 校验当前验证码是否有效
 */
@Transactional(readOnly = false)
public interface SecureCodeService {

  /**
   * 1分钟以上的全部清除掉
   */
  public static final int FIXED_RATE_STRING = 1 * 60;

  /**
   * 清除所有已过期的secure code
   */
  void cleanExpired();

  Boolean validity(String consumer, String code, SecureCodeSceneEnum type);


  SecureCode send(SecureCodeSendRequestBody body, SecureCodeSceneEnum type);


  void clean(String consumer, String code, SecureCodeSceneEnum type);

}
