package com.minlia.module.captcha.v1.service.impl;

import com.minlia.cloud.service.AbstractReadOnlyService;
import com.minlia.module.captcha.v1.dao.SecureCodeDao;
import com.minlia.module.captcha.v1.domain.SecureCode;
import com.minlia.module.captcha.v1.service.SecureCodeReadOnlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by will on 8/14/17.
 */
@Service
public class SecureCodeReadOnlyServiceImpl extends AbstractReadOnlyService<SecureCodeDao, SecureCode, Long> implements SecureCodeReadOnlyService {

    @Autowired
    SecureCodeDao secureCodeDao;

}
