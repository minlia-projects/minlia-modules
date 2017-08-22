package com.minlia.module.language.v1.service;

import com.minlia.cloud.service.WriteOnlyService;
import com.minlia.module.language.v1.dao.LanguageDao;
import com.minlia.module.language.v1.domain.Language;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 8/22/17.
 */
@Transactional(readOnly = false)
public interface LanguageWriteOnlyService extends WriteOnlyService<LanguageDao,Language,Long> {
}
