package com.minlia.module.language.v1.service.impl;

import com.minlia.module.data.batis.service.AbstractServiceImpl;
import com.minlia.module.language.v1.dao.LanguageDao;
import com.minlia.module.language.v1.domain.Language;
import com.minlia.module.language.v1.service.LanguageWriteOnlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author will
 * @date 8/14/17
 */
@Service
public class LanguageWriteOnlyServiceImpl extends
    AbstractServiceImpl<LanguageDao,Language> implements LanguageWriteOnlyService {

    @Autowired
    private LanguageDao userDao;


  @Override
  public Language findOneByBasenameAndLanguageAndCountryAndCode(String basename, String language,
      String country, String code) {
    return null;
  }
}
