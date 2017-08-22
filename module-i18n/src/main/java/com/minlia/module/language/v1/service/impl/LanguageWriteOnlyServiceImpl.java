package com.minlia.module.language.v1.service.impl;

import com.minlia.cloud.service.AbstractWriteOnlyService;
import com.minlia.module.language.v1.dao.LanguageDao;
import com.minlia.module.language.v1.domain.Language;
import com.minlia.module.language.v1.service.LanguageWriteOnlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by will on 8/14/17.
 */
@Service
public class LanguageWriteOnlyServiceImpl extends AbstractWriteOnlyService<LanguageDao, Language, Long> implements LanguageWriteOnlyService {

    @Autowired
    private LanguageDao userDao;


}
