//package com.minlia.module.language.v1.service.impl;
//
//import com.minlia.cloud.service.AbstractReadOnlyService;
//import com.minlia.module.language.v1.dao.LanguageDao;
//import com.minlia.module.language.v1.domain.Language;
//import com.minlia.module.language.v1.service.LanguageReadOnlyService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * Created by will on 8/14/17.
// */
//@Service
//public class LanguageReadOnlyServiceImpl extends AbstractReadOnlyService<LanguageDao, Language, Long> implements LanguageReadOnlyService {
//
//    @Autowired
//    LanguageDao dao;
//
//    @Override
//    public Language findOneByBasenameAndLanguageAndCountryAndCode(String basename, String language,
//        String country, String code) {
//        return dao.findOneByBasenameAndLanguageAndCountryAndCode(basename,language,country,code);
//    }
//}
