package com.minlia.module.country.mapper;


import com.github.pagehelper.PageInfo;
import com.minlia.module.country.entity.Country;
import com.minlia.module.country.ro.CountryQRO;

import java.util.List;

public interface CountryMapper {

    int create(Country country);

    int update(Country country);

    int delete(Long id);

    Country queryById(Long id);

    List<Country> queryList(CountryQRO qo);

    PageInfo<Country> queryPage(CountryQRO qo);

}
