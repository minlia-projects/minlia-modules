package com.minlia.module.captcha.v1.service.impl;

import com.minlia.cloud.service.AbstractWriteOnlyService;
import com.minlia.module.captcha.v1.dao.SecureCodeDao;
import com.minlia.module.captcha.v1.domain.SecureCode;
import com.minlia.module.captcha.v1.service.SecureCodeWriteOnlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by will on 8/14/17.
 */
@Service
public class SecureCodeWriteOnlyServiceImpl extends AbstractWriteOnlyService<SecureCodeDao, SecureCode, Long> implements SecureCodeWriteOnlyService {

    @Autowired
    private SecureCodeDao secureCodeDao;


}
