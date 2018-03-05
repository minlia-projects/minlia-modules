package com.minlia.module.language.service.impl;

import com.minlia.cloud.service.AbstractReadOnlyService;
import com.minlia.module.language.domain.Language;
import com.minlia.module.language.mapper.LanguageDao;
import com.minlia.module.language.service.LanguageReadOnlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by will on 8/14/17.
 */
@Service
public class LanguageReadOnlyServiceImpl extends AbstractReadOnlyService<LanguageDao, Language, Long> implements LanguageReadOnlyService {

    @Autowired
    LanguageDao dao;

    @Override
    public Language findOneByBasenameAndLanguageAndCountryAndCode(String basename, String language,
        String country, String code) {
        return dao.findOneByBasenameAndLanguageAndCountryAndCode(basename,language,country,code);
    }
}
