package com.minlia.module.language.service.impl;

import com.minlia.module.language.mapper.LanguageDao;
import com.minlia.module.language.service.LanguageWriteOnlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by will on 8/14/17.
 */
@Service
public class LanguageWriteOnlyServiceImpl implements LanguageWriteOnlyService {

    @Autowired
    private LanguageDao userDao;


}
