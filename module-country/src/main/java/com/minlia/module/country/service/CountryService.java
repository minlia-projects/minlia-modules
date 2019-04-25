package com.minlia.module.country.service;


import com.github.pagehelper.PageInfo;
import com.minlia.module.country.entity.Country;
import com.minlia.module.country.ro.CountryQRO;
import com.minlia.module.country.ro.CountryCRO;
import com.minlia.module.country.ro.CountryURO;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by will on 6/17/17.
 */
@Transactional
public interface CountryService {

    Country create(CountryCRO cto);

    Country update(CountryURO uro);

    int delete(Long id);

    Country queryById(Long id);

    List<Country> queryList(CountryQRO qo);

    PageInfo<Country> queryPage(CountryQRO qo, Pageable pageable);

}
