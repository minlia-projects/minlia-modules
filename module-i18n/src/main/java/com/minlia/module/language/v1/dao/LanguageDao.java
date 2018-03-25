package com.minlia.module.language.v1.dao;

import com.minlia.module.data.batis.mapper.AbstractMapper;
import com.minlia.module.language.v1.domain.Language;

/**
 * Created by will on 8/22/17.
 */
public interface LanguageDao extends AbstractMapper<Language> {

  Language findOneByBasenameAndLanguageAndCountryAndCode(String basename, String language,
      String country, String code);
}
