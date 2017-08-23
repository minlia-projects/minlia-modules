package com.minlia.module.language.v1.service;


import com.minlia.module.language.v1.domain.Language;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = false)
public interface LanguageCreationService {

  public Language initialLanguage(String code);

}
