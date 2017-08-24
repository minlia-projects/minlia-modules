package com.minlia.module.language.v1.service.impl;

import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.language.v1.constant.I18nConstant;
import com.minlia.module.language.v1.domain.Language;
import com.minlia.module.language.v1.service.LanguageCreationService;
import com.minlia.module.language.v1.service.LanguageReadOnlyService;
import com.minlia.module.language.v1.service.LanguageWriteOnlyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class LanguageCreationServiceImpl implements LanguageCreationService {

  @Autowired
  LanguageWriteOnlyService languageWriteOnlyService;


  @Autowired
  LanguageReadOnlyService languageReadOnlyService;


  @Override
  public Language initialLanguage(String code) {
    return initialLanguage(code, null);
  }

  @Override
  public Language initialLanguage(String code, String message) {
    return initialLanguage(null, null, code, message);
  }

  @Override
  public Language initialLanguage(String language, String country, String code, String message) {
    return initialLanguage(null, language, country,code, message);
  }

  public Language initialLanguage(String basename, String language, String country, String code,
      String message) {
    ApiPreconditions.checkNotNull(code, ApiCode.NOT_NULL);

    if (StringUtils.isEmpty(basename)) {
      basename = I18nConstant.BASENAME;
    }
    if (StringUtils.isEmpty(language)) {
      language = I18nConstant.DEFAULT_LANGUAGE;
    }
    if (StringUtils.isEmpty(country)) {
      country = I18nConstant.DEFAULT_COUNTRY;
    }
    if (StringUtils.isEmpty(message)) {
      message = "%%" + code + "%%";
    }
    Language languageEntity = languageReadOnlyService.findOneByBasenameAndLanguageAndCountryAndCode(
        basename, language, country, code);
    if (null == languageEntity) {
      languageEntity = new Language();
      languageEntity.setBasename(basename);
      languageEntity.setLanguage(language);
      languageEntity.setCountry(country);
      languageEntity.setCode(code);
      languageEntity.setMessage(message);
      languageEntity = languageWriteOnlyService.save(languageEntity);
    }
    return languageEntity;
  }

}
