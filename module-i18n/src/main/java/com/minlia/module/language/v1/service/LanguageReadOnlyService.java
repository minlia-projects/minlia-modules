package com.minlia.module.language.v1.service;

import com.minlia.cloud.service.ReadOnlyService;
import com.minlia.module.language.v1.dao.LanguageDao;
import com.minlia.module.language.v1.domain.Language;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 8/22/17.
 */
@Transactional(readOnly = true)
public interface LanguageReadOnlyService extends ReadOnlyService<LanguageDao, Language, Long> {
}
