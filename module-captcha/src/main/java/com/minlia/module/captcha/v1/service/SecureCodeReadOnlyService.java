package com.minlia.module.captcha.v1.service;

import com.minlia.cloud.service.ReadOnlyService;
import com.minlia.module.captcha.v1.dao.SecureCodeDao;
import com.minlia.module.captcha.v1.domain.SecureCode;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 8/22/17.
 */
@Transactional(readOnly = true)
public interface SecureCodeReadOnlyService extends ReadOnlyService<SecureCodeDao, SecureCode, Long> {
}
