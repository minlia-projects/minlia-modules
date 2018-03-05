package com.minlia.module.language.mapper;

import com.minlia.module.language.domain.Language;

/**
 * Created by will on 8/22/17.
 */
public interface LanguageDao {

  Language findOneByBasenameAndLanguageAndCountryAndCode(String basename, String language, String country, String code);

}
