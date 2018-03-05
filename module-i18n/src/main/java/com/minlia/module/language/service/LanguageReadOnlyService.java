package com.minlia.module.language.service;

import com.minlia.cloud.service.ReadOnlyService;
import com.minlia.module.language.domain.Language;
import com.minlia.module.language.mapper.LanguageDao;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 8/22/17.
 */
@Transactional(readOnly = true)
public interface LanguageReadOnlyService extends ReadOnlyService<LanguageDao, Language, Long> {

  Language findOneByBasenameAndLanguageAndCountryAndCode(String basename, String zh, String cn,
      String code);
}
