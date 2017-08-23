package com.minlia.module.language.v1.dao;

import com.minlia.cloud.dao.Dao;
import com.minlia.module.language.v1.domain.Language;

/**
 * Created by will on 8/22/17.
 */
public interface LanguageDao extends Dao<Language, Long> {

  Language findOneByBasenameAndLanguageAndCountryAndCode(String basename, String language,
      String country, String code);
}
