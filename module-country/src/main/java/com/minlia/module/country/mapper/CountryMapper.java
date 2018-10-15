package com.minlia.module.country.mapper;


import com.github.pagehelper.PageInfo;
import com.minlia.module.country.bean.domain.Country;
import com.minlia.module.country.bean.qo.CountryQO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CountryMapper {

    void create(Country country);

    void update(Country country);

    void delete(Long id);

    Country queryById(Long id);

    List<Country> queryList(CountryQO qo);

    PageInfo<Country> queryPage(CountryQO qo);

}
