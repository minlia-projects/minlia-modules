package com.minlia.module.language.v1.service.impl;

import com.minlia.module.language.v1.constant.I18nConstant;
import com.minlia.module.language.v1.domain.Language;
import com.minlia.module.language.v1.service.LanguageCreationService;
import com.minlia.module.language.v1.service.LanguageReadOnlyService;
import com.minlia.module.language.v1.service.LanguageWriteOnlyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LanguageCreationServiceImpl implements LanguageCreationService {

  @Autowired
  LanguageWriteOnlyService languageWriteOnlyService;


  @Autowired
  LanguageReadOnlyService languageReadOnlyService;


  @Override
  public Language initialLanguage(String code) {

    Language language= languageReadOnlyService.findOneByBasenameAndLanguageAndCountryAndCode(
        I18nConstant.BASENAME,I18nConstant.DEFAULT_LANGUAGE,I18nConstant.DEFAULT_COUNTRY,code);

    if(null ==language){
      language=new Language();
      language.setBasename( I18nConstant.BASENAME);
      language.setLanguage(I18nConstant.DEFAULT_LANGUAGE);
      language.setCountry(I18nConstant.DEFAULT_COUNTRY);
      language.setCode(code);
      language.setMessage("%%"+code+"%%");
      language= languageWriteOnlyService.save(language);
    }
   return language;
  }
}
