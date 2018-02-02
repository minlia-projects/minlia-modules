package com.minlia.module.country.mapper;


import com.github.pagehelper.PageInfo;
import com.minlia.module.country.body.CountryQueryRequestBody;
import com.minlia.module.country.entity.Country;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CountryMapper {

    void create(Country country);

    void update(Country country);

    void delete(Long id);

    Country queryById(Long id);

    List<Country> queryList(CountryQueryRequestBody body);

    PageInfo<Country> queryPage(CountryQueryRequestBody body);

}
