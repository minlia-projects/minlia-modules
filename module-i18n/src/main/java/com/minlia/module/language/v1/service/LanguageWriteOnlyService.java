package com.minlia.module.language.v1.service;

import com.minlia.module.data.batis.service.AbstractService;
import com.minlia.module.language.v1.domain.Language;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 8/22/17.
 */
@Transactional(readOnly = false)
public interface LanguageWriteOnlyService extends AbstractService<Language> {

  Language findOneByBasenameAndLanguageAndCountryAndCode(String basename, String language,
      String country, String code);
}
